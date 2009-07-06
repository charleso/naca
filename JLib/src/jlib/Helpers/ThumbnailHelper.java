/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.imageio.ImageIO;

import jlib.exception.ProgrammingException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

//*******************************************************************************
//**              Helper class to create thumbnails.                           **
//*******************************************************************************
/**
 * Helper class to create thumbnails.
 */
public class ThumbnailHelper {
//*************************************************************************************************
//**                 Creates a thumbnail from an image stored in the file system.                **
//*************************************************************************************************
/**
 * Creates a thumbnail from an image stored in the file system..
 * @param imageFile Location of the image in the file system.
 * @param thumbnailFile Destination file for the thumbnail.
 * @param maxHeight The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 * @param maxWidth The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 */
	public static void createThumbnail(File imageFile,File thumbnailFile,int maxHeight,int maxWidth) {
		Image image;
		try {
			image=ImageIO.read(imageFile);
		} catch (IOException e) {
			throw new ProgrammingException(ProgrammingException.INVALID_FILENAME,"'"+imageFile.getAbsolutePath()+"': "+e.getMessage(),e);
		}
		createThumbnail(image,thumbnailFile,maxHeight,maxWidth);
	}

//*************************************************************************************************
//**                 Creates a thumbnail from an image contained in an input stream.             **
//*************************************************************************************************
/**
 * Creates a thumbnail from an image contained in an input stream.
 * @param imageStream <code>InputStream</code> initialized on the binaries for the image.
 * @param thumbnailFile Destination file for the thumbnail.
 * @param maxHeight The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 * @param maxWidth The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 */
	public static void createThumbnail(Image image,File thumbnailFile,int maxHeight,int maxWidth) {
		FileOutputStream thumbnailStream;
		try {
			thumbnailStream=new FileOutputStream(thumbnailFile);
		} catch (FileNotFoundException e) {
			throw new ProgrammingException(ProgrammingException.INVALID_FILENAME,e.getLocalizedMessage(),e);
		}
		createThumbnail(image,thumbnailStream,maxHeight,maxWidth);
	}

//*************************************************************************************************
//**      Returns an InputStream from where retrieve the content of the thumbnail.               **
//*************************************************************************************************
/**
 * Returns an <code>InputStream</code> from where retrieve the content of the thumbnail.
 * @param imageStream <code>InputStream</code> initialized on the binaries for the image.
 * @param maxHeight The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 * @param maxWidth The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 * @return An <code>InputStream</code> from where retrieve the content of the thumbnail.
 */
	public static InputStream createThumbnail(Image image,int maxHeight,int maxWidth) {

//********************** Separate thread producing the thumbnail ************************
		class PipedThread extends Thread {
			private PipedOutputStream _pos;
			private Image _image;
			private int _maxHeight,_maxWidth;

//................................. Initialization ..............................
			public PipedThread(Image image,int maxHeight,int maxWidth) {
				_pos=new PipedOutputStream();
				_image=image;
				_maxHeight=maxHeight;
				_maxWidth=maxWidth;
			}

//........................ To obtain the PipedOutputStream ......................
			public PipedOutputStream getPipedOutputStream() {
				return _pos;
			}

//...................... Creates the thumbnail ................................
			@Override
			public void run() {
				createThumbnail(_image,_pos,_maxWidth,_maxHeight);
				try {
					_pos.close();
				} catch (IOException e) {
					throw new ProgrammingException(ProgrammingException.IO_ERROR,e.getMessage(),e);
				}
			}								
		}

//********* Launches a new thread performing the node serialization ***********
		PipedThread thread;
		PipedInputStream pis;
		thread=new PipedThread(image,maxHeight,maxWidth);
		try {
			pis = new PipedInputStream(thread.getPipedOutputStream());
		} catch (IOException e) {
			throw new ProgrammingException(ProgrammingException.IO_ERROR,e.getMessage(),e);
		}
		thread.start();
		return pis;		
	}

//*************************************************************************************************
//**                 Creates a thumbnail from an image contained in an input stream.             **
//*************************************************************************************************
/**
 * Creates a thumbnail from an image contained in an input stream.
 * @param imageStream <code>InputStream</code> initialized on the binaries for the image.
 * @param thumbnailStream <code>OutputStream</code> initialized and ready to receive the thumbnail's binary content.
 * @param maxHeight The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 * @param maxWidth The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 */
	public static void createThumbnail(InputStream imageStream,OutputStream thumbnailStream,int maxHeight,int maxWidth) {
		Image image;
		try {
			image=ImageIO.read(imageStream);
		} catch (IOException e) {
			throw new ProgrammingException(ProgrammingException.IO_ERROR,e.getMessage(),e);
		}
		createThumbnail(image,thumbnailStream,maxHeight,maxWidth);
	}

//*************************************************************************************************
//**                 Creates a thumbnail from an image contained in an input stream.             **
//*************************************************************************************************
/**
 * Creates a thumbnail from a <code>Image</code>.
 * @param image A loaded image.
 * @param thumbnailStream <code>OutputStream</code> initialized and ready to receive the thumbnail's binary content.
 * @param maxHeight The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 * @param maxWidth The produced thumbnail fits in a square of <code>maxHeight</code> pixels 
 * height and <code>maxWidth</code> pixels width. The produced thumbnail keeps the same height / witdth 
 * as the original image.
 */
	public static void createThumbnail(Image image,OutputStream thumbnailStream,int maxHeight,int maxWidth) {

//................ Calculates the needed down scaling .............................................
		double scaleX=maxHeight;
		scaleX/=image.getHeight(null);
		double scaleY=maxWidth;
		scaleY/=image.getWidth(null);
		double scale=Math.min(scaleX,scaleY);

// Calculates the thumbnail size:
		int scaledW = (int)(scale * image.getWidth(null));
		int scaledH = (int)(scale * image.getHeight(null));

// Calculates the transformation:
		AffineTransform transform = new AffineTransform();
		if (scale < 1.0d)
		    transform.scale(scale, scale);

//................... Builds the thumbnail ........................................................
		BufferedImage bufferedImage = new BufferedImage(scaledW, scaledH, BufferedImage.TYPE_INT_RGB);
		Graphics2D graph2d = bufferedImage.createGraphics();
		graph2d.drawImage(image, transform, null);
		graph2d.dispose();

// Encodes and saves the thumbnail
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(thumbnailStream);
        try {
        	encoder.encode(bufferedImage);
        } catch (IOException e) {
        	throw new ProgrammingException(ProgrammingException.IO_ERROR,"I/O exception while saving the thumbnail: "+e.getMessage(),e);
        }
	}
}
