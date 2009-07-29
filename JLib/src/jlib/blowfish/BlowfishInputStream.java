/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.blowfish;

import java.io.*;

/**
 * An InputStream that reads from a Blowfish encrypted file.
 * @author Dale Anson (danson@germane-software.com), February, 2002
 */
public class BlowfishInputStream extends InputStream {

   private PushbackInputStream _in;
   private String _passphrase;

   private BlowfishCBC _cbc;
   //private long _iv;

   private byte[] _in_buffer;
   private int _bytes_read = 0;
   private int _buffer_index = 0;
   private boolean _started = false;

   /**
    * @param passphrase the passphrase that was used to encrypt the original data.
    * @param is the input stream from which bytes will be read
    */
   public BlowfishInputStream(String passphrase, InputStream is) {
      _passphrase = passphrase;
      _in = new PushbackInputStream(new BufferedInputStream(is));

      // hash down the password to a 160bit key
      SHA1 hasher = new SHA1();
      hasher.update(_passphrase);
      hasher.finalize();

      // setup the encryptor (use a dummy IV)
      _cbc = new BlowfishCBC(hasher.getDigest(), 0);
      hasher.clear();  

      // create the input buffer
      _in_buffer = new byte[BlowfishCBC.BLOCKSIZE];
   }

   /**
    * Reads the next byte of data from this input stream. The value byte is returned
    * as an int in the range 0 to 255. If no byte is available because the end of the
    * stream has been reached, the value -1 is returned. This method blocks until
    * input data is available, the end of the stream is detected, or an exception is
    * thrown.
    * @return the next byte of data or -1 if the end of the stream has been reached.
    */
   public int read() throws IOException {
      if ( !_started ) {
         decryptBuffer();     // load the iv
         if ( _bytes_read < BlowfishCBC.BLOCKSIZE ) {
            return -1;
         }
         decryptBuffer();     // load the input buffer
         if ( _bytes_read == -1 ) {
            return -1;
         }
         _buffer_index = 0;
      }

      // check that all bytes from input stream have been returned
      if ( _bytes_read < _in_buffer.length && _buffer_index == _bytes_read ) {
         return -1;
      }

      // check if all bytes in buffer have been returned, if so,
      // need to refill the buffer
      if ( _buffer_index == _bytes_read ) {
         decryptBuffer();
         if ( _bytes_read == -1 ) {
            return -1;
         }
         _buffer_index = 0;
      }

      // return the next byte from the buffer
      int rtn = _in_buffer[_buffer_index] & 0xff;
      ++_buffer_index;
      return rtn;
   }

   /**
    * Reads enough bytes from the underlying input stream to decrypt, and then
    * decrypts it.
    */
   private void decryptBuffer() throws IOException {
      _bytes_read = _in.read(_in_buffer, 0, _in_buffer.length);
      if ( _bytes_read == -1 ) {
         return;
      }

      if ( !_started ) {
         // did the entire CBC IV get read?
         if ( _bytes_read < _in_buffer.length )
            return;
         // set the CBC IV, it is the first 8 bytes of the input stream
         long iv = BinConverter.byteArrayToLong(_in_buffer, 0);
         _cbc.setCBCIV(iv);
         _started = true;
         return;
      }

      // decrypt the buffer
      _cbc.decrypt(_in_buffer);

      // check for last block -- if the original data did not fit exactly into
      // an 8 byte block, the block was padded with enough bytes to fill the 
      // block, then encrypted. The last byte is ALWAYS the number of pad bytes,
      // that means the last block could be eight 8's, which is all padding.
      int end = _in.read();
      if ( end == -1 ) {
         // all done
         int pad_count = _in_buffer[_in_buffer.length - 1];
         if ( pad_count > _in_buffer.length || pad_count < 1 ) {
            // the last byte wasn't a number, so it must be good data
            return;
         }
         else {
            // adjust bytes read to reflect the number of 'good' bytes
            _bytes_read = _in_buffer.length - pad_count;
            if ( _bytes_read == 0 ) {
               _bytes_read = -1;
            }
         }
      }
      else {
         _in.unread(end);
      }
   }

   /**
    * @see java.io.InputStream
    */
   public boolean markSupported() {
      return _in.markSupported();
   }

   /**
    * @see java.io.InputStream
    */
   public void mark(int readlimit) {
      _in.mark(readlimit);
   }

   /**
    * @see java.io.InputStream
    */
   public int available() throws IOException {
      return _in.available();
   }

   /**
    * @see java.io.InputStream
    */
   public void close() throws IOException {
      _in.close();
      _cbc.cleanUp();
      return;
   }
}


     
