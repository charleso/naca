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
package jlib.Helpers;

import java.io.IOException;
import java.io.InputStream;

/**
 * Performs BASE64 to binary transformation on a InputStream.
*/

public class BASE64DecoderInputStream extends InputStream {

//	*************************************************************************
//	**                      The class constructor.                         **
//	*************************************************************************
/**
 * The constructor admits any <code>InputStream</code> connected
 * to a BASE64 source.
 * @param binaryInput An input connected to a Base64 source. 
 */
	public BASE64DecoderInputStream(InputStream base64Input) {
		_base64Input=base64Input;
		_buffer=new byte[3];
		_bufferIndex=-1;
		_dataCount=0;
		_base64=new byte[256];
		_base64[0]=127;_base64[1]=127;_base64[2]=127;_base64[3]=127;_base64[4]=127;_base64[5]=127;_base64[6]=127;_base64[7]=127;_base64[8]=127;_base64[9]=127;
		_base64[10]=127;_base64[11]=127;_base64[12]=127;_base64[13]=127;_base64[14]=127;_base64[15]=127;_base64[16]=127;_base64[17]=127;_base64[18]=127;_base64[19]=127; 
	    _base64[20]=127;_base64[21]=127;_base64[22]=127;_base64[23]=127;_base64[24]=127;_base64[25]=127;_base64[26]=127;_base64[27]=127;_base64[28]=127;_base64[29]=127; 
		_base64[30]=127;_base64[31]=127;_base64[32]=127;_base64[33]=127;_base64[34]=127;_base64[35]=127;_base64[36]=127;_base64[37]=127;_base64[38]=127;_base64[39]=127; 
		_base64[40]=127;_base64[41]=127;_base64[42]=127;_base64[43]=62;_base64[44]=127;_base64[45]=127;_base64[46]=127;_base64[47]=63;_base64[48]=52;_base64[49]=53;  
		_base64[50]=54;_base64[51]=55;_base64[52]=56;_base64[53]=57;_base64[54]=58;_base64[55]=59;_base64[56]=60;_base64[57]=61;_base64[58]=127;_base64[59]=127; 
		_base64[60]=127;_base64[61]=127;_base64[62]=127;_base64[63]=127;_base64[64]=127;_base64[65]=0;_base64[66]=1;_base64[67]=2;_base64[68]=3;_base64[69]=4;   
		_base64[70]=5;_base64[71]=6;_base64[72]=7;_base64[73]=8;_base64[74]=9;_base64[75]=10;_base64[76]=11;_base64[77]=12;_base64[78]=13;_base64[79]=14;  
		_base64[80]=15;_base64[81]=16;_base64[82]=17;_base64[83]=18;_base64[84]=19;_base64[85]=20;_base64[86]=21;_base64[87]=22;_base64[88]=23;_base64[89]=24;  
		_base64[90]=25;_base64[91]=127;_base64[92]=127;_base64[93]=127;_base64[94]=127;_base64[95]=127;_base64[96]=127;_base64[97]=26;_base64[98]=27;_base64[99]=28;  
		_base64[100]=29;_base64[101]=30;_base64[102]=31;_base64[103]=32;_base64[104]=33;_base64[105]=34;_base64[106]=35;_base64[107]=36;_base64[108]=37;_base64[109]=38; 
		_base64[110]=39;_base64[111]=40;_base64[112]=41;_base64[113]=42;_base64[114]=43;_base64[115]=44;_base64[116]=45;_base64[117]=46;_base64[118]=47;_base64[119]=48; 
		_base64[120]=49;_base64[121]=50;_base64[122]=51;_base64[123]=127;_base64[124]=127;_base64[125]=127;_base64[126]=127;_base64[127]=127;_base64[128]=127;_base64[129]=127;
		_base64[130]=127;_base64[131]=127;_base64[132]=127;_base64[133]=127;_base64[134]=127;_base64[135]=127;_base64[136]=127;_base64[137]=127;_base64[138]=127;_base64[139]=127;
		_base64[140]=127;_base64[141]=127;_base64[142]=127;_base64[143]=127;_base64[144]=127;_base64[145]=127;_base64[146]=127;_base64[147]=127;_base64[148]=127;_base64[149]=127;
		_base64[150]=127;_base64[151]=127;_base64[152]=127;_base64[153]=127;_base64[154]=127;_base64[155]=127;_base64[156]=127;_base64[157]=127;_base64[158]=127;_base64[159]=127;
		_base64[160]=127;_base64[161]=127;_base64[162]=127;_base64[163]=127;_base64[164]=127;_base64[165]=127;_base64[166]=127;_base64[167]=127;_base64[168]=127;_base64[169]=127;
		_base64[170]=127;_base64[171]=127;_base64[172]=127;_base64[173]=127;_base64[174]=127;_base64[175]=127;_base64[176]=127;_base64[177]=127;_base64[178]=127;_base64[179]=127;
		_base64[180]=127;_base64[181]=127;_base64[182]=127;_base64[183]=127;_base64[184]=127;_base64[185]=127;_base64[186]=127;_base64[187]=127;_base64[188]=127;_base64[189]=127;
		_base64[190]=127;_base64[191]=127;_base64[192]=127;_base64[193]=127;_base64[194]=127;_base64[195]=127;_base64[196]=127;_base64[197]=127;_base64[198]=127;_base64[199]=127;
		_base64[200]=127;_base64[201]=127;_base64[202]=127;_base64[203]=127;_base64[204]=127;_base64[205]=127;_base64[206]=127;_base64[207]=127;_base64[208]=127;_base64[209]=127;
		_base64[210]=127;_base64[211]=127;_base64[212]=127;_base64[213]=127;_base64[214]=127;_base64[215]=127;_base64[216]=127;_base64[217]=127;_base64[218]=127;_base64[219]=127;
		_base64[220]=127;_base64[221]=127;_base64[222]=127;_base64[223]=127;_base64[224]=127;_base64[225]=127;_base64[226]=127;_base64[227]=127;_base64[228]=127;_base64[229]=127;
		_base64[230]=127;_base64[231]=127;_base64[232]=127;_base64[233]=127;_base64[234]=127;_base64[235]=127;_base64[236]=127;_base64[237]=127;_base64[238]=127;_base64[239]=127;
		_base64[240]=127;_base64[241]=127;_base64[242]=127;_base64[243]=127;_base64[244]=127;_base64[245]=127;_base64[246]=127;_base64[247]=127;_base64[248]=127;_base64[249]=127;
		_base64[250]=127;_base64[251]=127;_base64[252]=127;_base64[253]=127;_base64[254]=127;
	}

//	*************************************************************************
//	**                         Class properties.                           **
//	*************************************************************************

//	***************************** The data input ****************************
/**
 * Contains the data source.
 * The source is assumed to be base64, so retrieved data are considered
 * as bytes.
 */
	private InputStream _base64Input;

//	**************************** The conversion table ***********************
/**
 * The 6bit base64 encoding table.
 */	
	static byte _base64[]; 

	
//	***************** Buffers for differed input and output *****************
/**
 * BASE64 consists of encoding 3 bytes from the input into 4 bytes to the
 * output. As the output is read one byte at a time ({@link #read()}, a buffer
 * is needed for absorbing the difference in the read rythm.
 */
	private byte _buffer[];
	private int _bufferIndex;

//	********** Counts the number of data read from the stream ***************
/**
 * Counts the number of data read from the input source.
 * This information can't be retrieved from the base64 encoding, as
 * the number of bytes varies (3 bytes input are 4 bytes output, plus
 * the padding).
 */
	private int _dataCount;
//	*************************************************************************
//	**                The actual data transformation                       **
//	*************************************************************************
/**
 * Transforms the data from the provided input source (see {@link #BASE64InputStream(InputStream)}
 * into a base64 string.
 */
	@Override
	public int read() throws IOException {
		int data;
//	***************** Fills the intermediate buffer with data **************
		if (_bufferIndex<0) {
			long union;            // A long to perform the binary conversion.
			byte b;
			int n;                 // For retrieving 4 data from the input.
			int padding;           // Number of "=" to pad at the end of the encoded string.
			
//	................ Reads four bytes from the source .....................
			padding=0;
			union=0;
			for(n=0;n<4;n++) {
				data=_base64Input.read();
//	 If there are no more data coming from the source:
				if (data<0){
					if (n==0)
						return -1;
					else
						throw new IOException("Unexpected end of file.");
				}
				if (data==61) {
					padding++;
					data=0;
				}
//transform data in a 6 bytes caracters
				else {
					b=(byte)data;
					data =_base64[b];
				}

//	 Keeps counting the bytes retrieved from the source.
				_dataCount++;

//	 Feeds the read data (6bit) into the union:
				data&=63;
				union<<=6;
				union|=data;
			}

//	................ Reads three bytes from the union .......................
			for(n=0;n<padding;n++)
				union>>>=8;

			for(n=padding;n<3;n++) {
				data=(int)(union & 255);
				b=(byte)data;
				_buffer[n-padding]=b;
				union>>>=8;
			}
			_bufferIndex=2-padding;
		}

//	****************** Returns one byte from the intermediate buffer *******
		data=_buffer[_bufferIndex] & 255;
		_bufferIndex--;
		return data;
	}

//	************************************************************************
//	**         Returns the number of bytes read from the source.          **
//	************************************************************************
/**
 * Returns the number of bytes read from the source.
 * This information can't be retrieved from the base64 encoding, as
 * the number of bytes varies (4 bytes input are 3 bytes output, plus
 * the padding).
 */
	public int getDataCount() {
		return _dataCount;
	}



}
