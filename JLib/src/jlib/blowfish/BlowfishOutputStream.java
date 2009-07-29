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
import java.util.*;

/**
 * An OutputStream that encrypts data using the Blowfish algorithm.
 * 30 Mar 2002, fixed bug in flush method
 * @author Dale Anson (danson@germane-software.com), February, 2002
 */
public class BlowfishOutputStream extends OutputStream {

   private OutputStream _out;
   private String _passphrase;

   private BlowfishCBC _cbc;
   private long _iv;

   byte[] _in_buffer;
   byte [] _out_buffer;
   int _bytes_in_buffer = 0;
   boolean _started = false;

   /**
    * @param passphrase the password to use to encrypt the data
    * @param os the OutputStream to write the data to
    */
   public BlowfishOutputStream( String passphrase, OutputStream os ) {
      _passphrase = passphrase;
      _out = os;

      // swiped from BlowfishEasy
      // hash down the password to a 160bit key
      SHA1 hasher = new SHA1();
      hasher.update( _passphrase );
      hasher.finalize();

      // setup the encryptor (use a dummy IV)
      _cbc = new BlowfishCBC( hasher.getDigest(), 0 );
      hasher.clear();

      _iv = new Random().nextLong();
      _in_buffer = new byte[ BlowfishCBC.BLOCKSIZE ];
      _out_buffer = new byte[ BlowfishCBC.BLOCKSIZE ];
   }

   /**
    * Writes the specified byte to this output stream. The general contract for write
    * is that one byte is written to the output stream. The byte to be written is
    * the eight low-order bits of the argument b. The 24 high-order bits of b are
    * ignored.
    * @param b the byte to write
    */
   public void write( int b ) throws IOException {
      // make sure the iv is written to output stream -- this is always the
      // first 8 bytes written out.
      if ( !_started ) {
         byte[] iv_bytes = new byte[ BlowfishCBC.BLOCKSIZE ];
         BinConverter.longToByteArray( _iv, iv_bytes, 0 );
         _out.write( iv_bytes, 0, iv_bytes.length );
         _cbc.setCBCIV( _iv );
         _started = true;
      }

      // if buffer isn't full, just store the input
      ++_bytes_in_buffer;
      if ( _bytes_in_buffer < _in_buffer.length ) {
         _in_buffer[ _bytes_in_buffer - 1 ] = ( byte ) b;
         return ;
      }

      // else this input will fill the buffer
      _in_buffer[ _bytes_in_buffer - 1 ] = ( byte ) b;
      _bytes_in_buffer = 0;

      // encrypt the buffer
      _cbc.encrypt( _in_buffer, _out_buffer );

      // write the out_buffer to the wrapped output stream
      for ( int i = 0; i < _out_buffer.length; i++ ) {
         _out.write( _out_buffer[ i ] );
      }
      return ;
   }

   /**
    * This method calls flush(), so there is no need to call both.
    * @see java.io.InputStream
    */
   public void close() throws IOException {
      // This output stream always writes out even blocks of 8 bytes. If it
      // happens that the last block does not have 8 bytes, then the block will
      // be padded to have 8 bytes.
      // The last byte is ALWAYS the number of pad bytes and will ALWAYS be a
      // number between 1 and 8, inclusive. If this means adding
      // an extra block just for the pad count, then so be it.
      // Minor correction: 8 isn't the magic number, rather it's BlowfishECB.BLOCKSIZE.
      byte pad_val = ( byte ) ( _in_buffer.length - _bytes_in_buffer );
      if ( pad_val > 0 ) {
         while ( _bytes_in_buffer < _in_buffer.length ) {
            _in_buffer[ _bytes_in_buffer ] = pad_val;
            ++ _bytes_in_buffer;
         }
         // encrypt the buffer
         _cbc.encrypt( _in_buffer, _out_buffer );
         // write the out_buffer to the wrapped output stream
         for ( int i = 0; i < _out_buffer.length; i++ ) {
            _out.write( _out_buffer[ i ] );
         }
      }
      flush();
      _out.close();
      _cbc.cleanUp();
      return ;
   }

   /**
    * Flushes this output stream and causes any buffered bytes to be written.
    */
   public void flush() throws IOException {
      _out.flush();
      return ;
   }
}

     
