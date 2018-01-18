package com.onwing.household.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;

import sun.net.util.IPAddressUtil;

/**
 * The <code>OutputFormatter</code> class has a collection of utility functions
 * that assist in the conversion of one format or representation to another.
 * While the name conveys that is to be used for output, this is not true. The
 * functions that exist here are not strictly for output purposes.
 * 
 * The <code>OutputFormatter</code> class can be instantiated but there is no
 * purpose in this as all methods are static.
 */
public final class OutputFormatter {
  /**
   * Create a new <code>OutputFormatter</code> object. Not necessary since all
   * methods are static.
   */
  public OutputFormatter() {
	// intentionally left blank
  }

  /**
   * Converts a string containing hex values to an array of bytes as follows:
   * 
   * 001122ff -> [0][17][34][255]
   * 
   * Notes: only accepts a hex string with an even number of characters
   * 
   * @param hexString
   *          The string that needs to be converted.
   * 
   * @return The byte (binary) representation of the hex values.
   */
  public final static byte[] hexToBinary(String hexString) {
	byte[] binaryValue = new byte[hexString.length() / 2];

	for (int i = 0, bytePosition = 0; i < hexString.length(); i += 2, bytePosition++) {
	  String byteString = hexString.substring(i, i + 2);

	  binaryValue[bytePosition] = (byte) (Integer.valueOf(byteString, 16).intValue() & 0xff);
	}

	return binaryValue;
  }

  /**
   * Converts a string containing hex values to an array of bytes as follows:
   * 
   * 00:11:22:ff -> [0][17][34][255]
   * 
   * Notes: only accepts a hex string with an even number of characters
   * 
   * @param hexString
   *          The string that needs to be converted.
   * 
   * @return The byte (binary) representation of the hex values.
   */
  public static byte[] hexToBinary(String hexString, String separator) {
	hexString = hexString.replaceAll(separator, "");

	return hexToBinary(hexString);

  }

  /**
   * Converts an array of bytes into a hex string as follows:
   * 
   * [0][17][34][255] -> 001122ff
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * 
   * @returns A string representing the binary values in hex format
   */
  public static String binaryToHex(byte[] binaryValue) {
	return binaryToHex(binaryValue, null);
  }

  /**
   * Converts an array of bytes into a hex string, and save the result string to
   * the buffer.
   * 
   * [0][17][34][255] -> 001122ff
   * 
   * 
   * @param buffer
   *          the buffer to save the result string.
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * 
   */
  public static void binaryToHex(StringBuilder buffer, byte[] binaryValue) {
	binaryToHex(buffer, binaryValue, null);
  }

  /**
   * Converts an array of bytes into a hex string with a separator.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the binary values in hex format
   */
  public static String binaryToHex(byte[] binaryValue, String separator) {
	// Use StringBuffer because it's faster
	StringBuffer buffer = new StringBuffer();

	// For every byte in the array, extract the upper and lower nibbles
	// separately.
	for (int i = 0; i < binaryValue.length; i++) {
	  // Extract Upper nibble first (since we read left to right)
	  buffer.append(_DIGIT_CHARS[(binaryValue[i] >> NIBBLE_SHIFT)
		                         & LOWER_NIBBLE_MASK]);
	  buffer.append(_DIGIT_CHARS[binaryValue[i] & LOWER_NIBBLE_MASK]);

	  if ((separator != null) && i < (binaryValue.length - 1)) {
		buffer.append(separator);
	  }
	}

	// and finally give the user what he wants
	return buffer.toString();
  }

  /**
   * Converts an array of bytes into a hex string with a separator.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the binary values in hex format
   */
  public static void binaryToHex(StringBuilder buffer, byte[] binaryValue,
	  String separator) {
	// Use StringBuffer because it's faster
	// StringBuffer buffer = new StringBuffer();

	// For every byte in the array, extract the upper and lower nibbles
	// separately.
	for (int i = 0; i < binaryValue.length; i++) {
	  // Extract Upper nibble first (since we read left to right)
	  buffer.append(_DIGIT_CHARS[(binaryValue[i] >> NIBBLE_SHIFT)
		                         & LOWER_NIBBLE_MASK]);
	  buffer.append(_DIGIT_CHARS[binaryValue[i] & LOWER_NIBBLE_MASK]);

	  if ((separator != null) && i < (binaryValue.length - 1)) {
		buffer.append(separator);
	  }
	}

	// and finally give the user what he wants
	// return buffer.toString();
  }

  /**
   * Converts an array of bytes into a hex string with a separator.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the binary values in hex format
   */
  public final static String binaryToHex(byte binaryValue) {
	// Use StringBuffer because it's faster
	StringBuilder buffer = new StringBuilder();
	buffer.append(_DIGIT_CHARS[(binaryValue >> NIBBLE_SHIFT)
	                           & LOWER_NIBBLE_MASK]);
	buffer.append(_DIGIT_CHARS[binaryValue & LOWER_NIBBLE_MASK]);

	return buffer.toString();
  }

  /**
   * Converts an array of bytes into a hex string with a separator, and save the
   * result string to the buffer.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * 
   * @param buffer
   *          the buffer to save the result string.
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the binary values in hex format
   */
  public final static void binaryToHex(StringBuilder buffer, byte binaryValue) {
	// Use StringBuffer because it's faster
	// StringBuilder buffer = new StringBuilder();
	buffer.append(_DIGIT_CHARS[(binaryValue >> NIBBLE_SHIFT)
	                           & LOWER_NIBBLE_MASK]);
	buffer.append(_DIGIT_CHARS[binaryValue & LOWER_NIBBLE_MASK]);
  }

  /**
   * Converts an array of bytes into a hex string with a separator.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * @param offset
   *          the index of the first byte to encode
   * @param length
   *          the number of bytes to encode
   * 
   * @returns A string representing the binary values in hex format
   */
  public static String binaryToHex(byte[] binaryValue, String separator,
	  int offset, int length) {
	// Use StringBuffer because it's faster
	StringBuffer buffer = new StringBuffer();

	int endIndex = Math.min(offset + length, binaryValue.length);
	// For every byte in the array, extract the upper and lower nibbles
	// separately.
	for (int i = offset; i < endIndex; i++) {
	  // Extract Upper nibble first (since we read left to right)
	  buffer.append(_DIGIT_CHARS[(binaryValue[i] >> NIBBLE_SHIFT)
		                         & LOWER_NIBBLE_MASK]);
	  buffer.append(_DIGIT_CHARS[binaryValue[i] & LOWER_NIBBLE_MASK]);

	  if ((separator != null) && i < (binaryValue.length - 1)) {
		buffer.append(separator);
	  }
	}

	// and finally give the user what he wants
	return buffer.toString();

  }

  /**
   * Converts an array of bytes into a hex string with a separator.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * and save the result string to buffer.
   * 
   * @param buffer
   *          the buffer to save the result string.
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * @param offset
   *          the index of the first byte to encode
   * @param length
   *          the number of bytes to encode
   * 
   * @returns A string representing the binary values in hex format
   */
  public static void binaryToHex(StringBuilder buffer, byte[] binaryValue,
	  String separator, int offset, int length) {
	// Use StringBuffer because it's faster
	// StringBuffer buffer = new StringBuffer();

	int endIndex = Math.min(offset + length, binaryValue.length);
	// For every byte in the array, extract the upper and lower nibbles
	// separately.
	for (int i = offset; i < endIndex; i++) {
	  // Extract Upper nibble first (since we read left to right)
	  buffer.append(_DIGIT_CHARS[(binaryValue[i] >> NIBBLE_SHIFT)
		                         & LOWER_NIBBLE_MASK]);
	  buffer.append(_DIGIT_CHARS[binaryValue[i] & LOWER_NIBBLE_MASK]);

	  if ((separator != null) && i < (binaryValue.length - 1)) {
		buffer.append(separator);
	  }
	}

	// and finally give the user what he wants
	// return buffer.toString();

  }

  /**
   * Converts an array of bytes into a hex string as follows:
   * 
   * [0][17][34][255] -> 001122ff
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * 
   * @returns A string representing the binary values in hex format
   */
  public static String binaryToHex(int[] binaryValue) {
	return binaryToHex(binaryValue, null);
  }

  /**
   * Converts an array of bytes into a hex string with a separator.
   * 
   * Example: 00:11:22:ff = binaryToHex([0][17][34][255], ":");
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the binary values in hex format
   */
  public final static String binaryToHex(int[] binaryValue, String separator) {
	// Use StringBuffer because it's faster
	StringBuffer buffer = new StringBuffer();

	// For every byte in the array, extract the upper and lower nibbles
	// separately.
	for (int i = 0; i < binaryValue.length; i++) {
	  // Extract Upper nibble first (since we read left to right)
	  buffer.append(_DIGIT_CHARS[(binaryValue[i] >> NIBBLE_SHIFT)
		                         & LOWER_NIBBLE_MASK]);
	  buffer.append(_DIGIT_CHARS[binaryValue[i] & LOWER_NIBBLE_MASK]);

	  if ((separator != null) && i < (binaryValue.length - 1)) {
		buffer.append(separator);
	  }

	}

	// and finally give the user what he wants
	return buffer.toString();
  }

  /**
   * Converts an array of bytes into a base10 string with a separator.
   * 
   * Example: 0.17.34.255 = binaryToHex([0][17][34][255], ".");
   * 
   * @param hexValue
   *          The hex value that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the hex value with separator
   */
  public static String formatHexValue(String hexValue, String separator) {
	byte[] binaryBytes = hexToBinary(hexValue);
	return binaryToHex(binaryBytes, separator);
  }

  /**
   * Converts an array of bytes into a base10 string with a separator.
   * 
   * Example: 0.17.34.255 = binaryToHex([0][17][34][255], ".");
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * @param separator
   *          The string to insert between the each bytes.
   * 
   * @returns A string representing the binary values in base10 format
   */
  public static String binaryToBase10(byte[] binaryValue, String separator) {
	// Use StringBuffer because it's faster
	StringBuffer buffer = new StringBuffer();

	// For every byte in the array, extract the upper and lower nibbles
	// separately.
	for (int i = 0; i < binaryValue.length; i++) {
	  // Is this greater than the normal range for a byte?
	  if ((binaryValue[i] & _SIGN_MASK) == _SIGN_MASK) {
		// Yes...manipulate it so that we don't get a negative number
		buffer.append((binaryValue[i] & ~(byte) _SIGN_MASK) + Byte.MAX_VALUE
		              + 1);
	  } else {
		buffer.append(Integer.toString(binaryValue[i]));
	  }

	  // and the separator
	  if (i < (binaryValue.length - 1) && (separator != null)) {
		buffer.append(separator);
	  }
	}

	// and finally give the user what he wants
	return buffer.toString();
  }

  /**
   * Converts an integer into a binary array
   * 
   * @param number
   *          the integer number that needs to be converted.
   * 
   * @return The byte representation of the integer.
   */
  public static byte[] intToBytes(int number) {
	byte[] binaryValues = new byte[_BYTES_IN_INT];

	final byte BYTE_MASK = (byte) 0xff;

	for (int i = 0; i < _BYTES_IN_INT; i++) {
	  // Shift to get each of four bytes of the integer
	  binaryValues[i] = (byte) ((number >> (_BITS_IN_BYTE * (_BYTES_IN_INT - i - 1))) & BYTE_MASK);
	}

	return binaryValues;
  }

  /**
   * Invert all the bits in an unsigned byte.
   * 
   * @param byteToInvert
   *          Byte whose bits will be inverted
   * 
   * 
   * @return The inverted form of the byte
   * */
  public static byte invertByte(byte byteToInvert) {
	return (byte) (byteToInvert ^ 0xFF);
	// final int BYTE_SCOPE = 256;
	//
	// int invertedInt = 0;
	//
	// if (byteToInvert > 0)
	// {
	// invertedInt = BYTE_SCOPE - byteToInvert - 1;
	// }
	// else
	// {
	// // byte value will be a negative if the most significant bit is set
	// invertedInt = -byteToInvert - 1;
	// }
	//
	// byte[] invertedBytes = intToBytes(invertedInt);
	//
	// return invertedBytes[_BYTES_IN_INT - 1];
  }

  /**
   * Converts a byte to integer
   * 
   * @param singleByte
   *          The byte that needs to be converted.
   * 
   * @return The integer representation of the byte
   */
  public static int byteToInt(byte singleByte) {
	final int BYTE_SCOPE = 256;

	// This will be result
	int value = singleByte;

	// When a number big than 128, it becomes a negative number
	// it need to be adjusted here.
	if (singleByte < 0) {
	  value = BYTE_SCOPE + singleByte;
	}

	return value;
  }

  /**
   * Converts a byte array to integer
   * 
   * @param bytes
   *          the bytes that needs to be converted. Only the first
   *          <code>BYTES_IN_INT</code> are converted.
   * 
   * @return the integer representation of the bytes
   */
  public static int bytesToInt(byte[] bytes, int offset) {
	final int BYTE_SCOPE = 256;

	// This will be result
	int value = 0;

	int temperary = 0;

	int maxOffset = offset + _BYTES_IN_INT;

	for (int i = offset; (i < maxOffset) && (i < bytes.length); i++) {
	  temperary = bytes[i];

	  // When a number big than 128, it becomes a negative number
	  // it need to be adjusted here.
	  if (bytes[i] < 0) {
		temperary = BYTE_SCOPE + bytes[i];
	  }

	  value = (value << _BITS_IN_BYTE) | temperary;
	}

	return value;
  }

  /**
   * Converts a byte array to integer
   * 
   * @param bytes
   *          the bytes that needs to be converted. Only the first
   *          <code>BYTES_IN_INT</code> are converted.
   * 
   * @return the integer representation of the bytes
   */
  public static int bytesToInt(byte[] bytes) {
	final int BYTE_SCOPE = 256;

	// This will be result
	int value = 0;

	int temperary = 0;

	for (int i = 0; (i < _BYTES_IN_INT) && (i < bytes.length); i++) {
	  temperary = bytes[i];

	  // When a number big than 128, it becomes a negative number
	  // it need to be adjusted here.
	  if (bytes[i] < 0) {
		temperary = BYTE_SCOPE + bytes[i];
	  }

	  value = (value << _BITS_IN_BYTE) | temperary;
	}

	return value;
  }

  /**
   * Converts a byte array to long
   * 
   * @param bytes
   *          the bytes that needs to be converted. Only the first
   *          <code>_BYTES_IN_LONG</code> are converted.
   * 
   * @return the long representation of the bytes
   */
  public static long bytesToLong(byte[] bytes) {
	final int BYTE_SCOPE = 256;

	// This will be result
	long value = 0;

	int temperary = 0;

	for (int i = 0; (i < _BYTES_IN_LONG) && (i < bytes.length); i++) {
	  temperary = bytes[i];

	  // When a number big than 128, it becomes a negative number
	  // it need to be adjusted here.
	  if (bytes[i] < 0) {
		temperary = BYTE_SCOPE + bytes[i];
	  }

	  value = (value << _BITS_IN_BYTE) | temperary;
	}

	return value;
  }

  /**
   * Converts a short into a byte array
   * 
   * @param number
   *          The short number that needs to be converted.
   * 
   * @return An array of bytes representing the short.
   */
  public static byte[] shortToBytes(short number) {
	byte[] binaryValues = new byte[_BYTES_IN_SHORT];

	final byte BYTE_MASK = (byte) 0xff;

	for (int i = 0; i < _BYTES_IN_SHORT; i++) {
	  // Shift to get each of four bytes of the integer
	  binaryValues[i] = (byte) ((number >> (_BITS_IN_BYTE * (_BYTES_IN_SHORT
		                                                     - i - 1))) & BYTE_MASK);
	}

	return binaryValues;
  }

  /**
   * Converts an array of bytes to short. If the array contains more bytes than
   * fits in a short, only those bytes that would comprise the short are
   * converted.
   * 
   * @param binaryValues
   *          The bytes that needs to be converted.
   * 
   * @returns A short representing the bytes.
   */
  public static short bytesToShort(byte[] bytes, int offset) {
	// final int BYTE_SCOPE = 256;

	// This will be result
	int value = 0;

	int unsignedByte = 0;

	int maxOffset = offset + _BYTES_IN_SHORT;

	for (int i = offset; (i < maxOffset) && (i < bytes.length); i++) {
	  unsignedByte = byteToUnsignedByte(bytes[i]);

	  value = (value << _BITS_IN_BYTE) | unsignedByte;
	}

	return new Integer(value).shortValue();
  }

  /**
   * Convert US-ASCII char (bytes) to string.
   * 
   * @param bytes
   *          the array of bytes that needs to been converted
   * 
   * 
   * @return the string
   */
  public static String charsToString(byte[] bytes) {
	StringBuilder builder = new StringBuilder(bytes.length);
	for (int i = 0; i < bytes.length; i++) {
	  builder.append((char) (bytes[i] & 0x0FF));
	}
	return builder.toString();
  }

  /**
   * Convert char array to byte array.
   * 
   * @param bytes
   *          the array of bytes that needs to been converted
   * 
   * 
   * @return the string
   */
  public static byte[] charsToBytes(char[] chars) {
	byte[] bytes = new byte[chars.length * 2];

	for (int i = 0, j = 0; i < chars.length; i++) {
	  bytes[j++] = (byte) (chars[i] >> 8);
	  bytes[j++] = (byte) (chars[i]);
	}
	return bytes;
  }

  /**
   * Convert char array to byte array.
   * 
   * @param bytes
   *          the array of bytes that needs to been converted
   * 
   * 
   * @return the string
   */
  public static char[] bytesToChars(byte[] bytes) {
	char[] chars = new char[bytes.length / 2];
	for (int i = 0, j = 0; i < chars.length; i++) {
	  chars[i] = (char) ((bytes[j++] << 8) | (bytes[j++] & 0xff));
	}
	return chars;
  }

  /**
   * Convert null-terminated US-ASCII chars (bytes) to string.
   * 
   * @param buf
   *          the buffer of bytes that needs to been converted
   * 
   * 
   * @return the string
   */
  public static String nullTerminatedCharsToString(ByteBuffer buf, int length) {
	int offset = buf.position();
	StringBuilder builder = new StringBuilder(length);
	byte ch;
	for (int i = 0; i < length; i++) {
	  ch = buf.get();
	  if (ch != _NULL_TERMINAL_CHAR) {
		builder.append((char) (ch & 0x0FF));
	  } else {
		buf.position(offset + length);
		break;
	  }
	}
	return builder.toString();
  }

  /**
   * Convert null-terminated US-ASCII chars (bytes) to string.
   * 
   * @param str
   *          the array of bytes that needs to been converted
   * 
   * 
   * @return the string
   */
  public static String nullTerminatedCharsToString(byte[] bytes) {
	StringBuilder builder = new StringBuilder(bytes.length);
	byte ch;
	for (int i = 0; i < bytes.length; i++) {
	  ch = bytes[i];
	  if (ch != _NULL_TERMINAL_CHAR) {
		builder.append((char) (ch & 0x0ff));
	  } else {
		break;
	  }
	}
	return builder.toString();
  }

  /**
   * Convert null-terminated US-ASCII chars (bytes) to string.
   * 
   * @param str
   *          the array of bytes that needs to been converted
   * 
   * 
   * @return the string
   */
  public static String nullTerminatedCharsToString(byte[] bytes, int offset,
	  int length) {
	StringBuilder builder = new StringBuilder(length);
	byte ch;
	for (int i = 0; i < length; i++) {
	  ch = bytes[offset + i];
	  if (ch != _NULL_TERMINAL_CHAR) {
		builder.append((char) (ch & 0x0FF));
	  } else {
		break;
	  }
	}
	return builder.toString();
  }

  /**
   * Convert string to null-terminated char (byte) array.
   * 
   * @param src
   *          the source string to be converted
   * @return the byte array with converted bytes
   */
  public static byte[] stringToNullTerminatedChars(String src) {
	byte[] result = new byte[src.length() + 1];
	for (int i = 0, size = result.length - 1; i < size; i++) {
	  result[i] = (byte) src.charAt(i);
	}
	return result;
  }

  /**
   * Convert string to null-terminated char (byte) array.
   * 
   * @param src
   *          the source string to be converted
   * @param dest
   *          the destination buffer to save converted chars
   */
  public static void stringToNullTerminatedChars(String src, byte[] dest) {
	stringToNullTerminatedChars(src, dest, 0, dest.length);
  }

  /**
   * Convert string to null-terminated char (byte) array.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   */
  public static void stringToNullTerminatedChars(String src, byte[] dst,
	  int length) {
	stringToNullTerminatedChars(src, dst, 0, length);
  }

  /**
   * Convert string to bytes with UTF-8 encoding.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   */
  public static byte[] stringToUtf8Bytes(String src) {

	byte[] bytes = null;

	try {
	  bytes = src.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	  // do nothing;
	}
	return bytes;
  }

  /**
   * Convert null terminated bytes with utf8 encoding to String.
   * 
   * @param src
   *          the source bytes to be converted
   * @return the string represent of the utf8 bytes
   */
  public static String utf8BytesToString(byte[] src, int offset, int length) {

	int validCharLength = findValidCharLength(src, offset, length);
	if (validCharLength == 0) {
	  return "";
	}

	String result;
	try {
	  result = new String(src, offset, validCharLength, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	  // do nothing;
	  result = null;
	}
	return result;
  }

  /**
   * Convert null terminated bytes with UTF-8 encoding to String.
   * 
   * @param src
   *          the source bytes to be converted
   * @return the string represent of the utf8 bytes
   */
  public static String utf8BytesToString(ByteBuffer src, int length) {

	byte[] bytes = new byte[length];
	src.get(bytes);
	return utf8BytesToString(bytes, 0, length);
  }

  /**
   * Converts null terminated bytes with utf8 encoding to string.
   * 
   * @param src
   *          the source bytes to be converted
   * @return the string represent of the utf8 bytes
   */
  public final static String utf8BytesToString(byte[] src) {
	return utf8BytesToString(src, 0, src.length);
  }

  /**
   * Convert string to bytes with UTF-8 encoding.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   */
  public static void stringToUtf8Bytes(String src, byte[] dst) {
	stringToUtf8Bytes(src, dst, 0, dst.length);
  }

  /**
   * Convert string to bytes with UTF-8 encoding.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param length
   *          the size of destination buffer that can be used to save converted
   *          chars.
   */
  public static void stringToUtf8Bytes(String src, byte[] dst, int length) {
	stringToUtf8Bytes(src, dst, 0, length);
  }

  /**
   * Convert string to bytes with UTF-8 encoding.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param dstOffset
   *          the beginning position of destination buffer to save converted
   *          chars
   * @param length
   *          the size of destination buffer that can be used to save converted
   *          chars.
   */
  public static void stringToUtf8Bytes(String src, byte[] dst, int dstOffset,
	  int length) {
	byte[] bytes = null;
	try {
	  bytes = src.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	  // do nothing;
	}

	if (bytes.length < length) {
	  System.arraycopy(bytes, 0, dst, dstOffset, bytes.length);
	} else {
	  System.arraycopy(bytes, 0, dst, dstOffset, length);
	}
  }

  /**
   * Convert string to null-terminated char (byte) array.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param dstOffset
   *          the beginning position of destination buffer to save converted
   *          chars
   * @param length
   *          the size of destination buffer that can be used to save converted
   *          chars.
   */
  public static void stringToNullTerminatedChars(String src, byte[] dst,
	  int dstOffset, int length) {
	int srclen = src.length();
	if (srclen >= length) {
	  srclen = length - 1;
	}
	for (int i = 0; i < srclen; i++) {
	  dst[dstOffset + i] = (byte) src.charAt(i);
	}
  }

  /**
   * Convert string to bytes with UTF-8 encoding
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param length
   *          the length of destination buffer that be used to save converted
   *          chars.
   */
  public static void stringToUtf8Bytes(String src, ByteBuffer dst, int length) {

	byte[] bytes = null;
	try {
	  bytes = src.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	  // do nothing;
	}
	if (bytes.length < length) {
	  int begin = dst.position();
	  dst.put(bytes, 0, bytes.length);
	  dst.position(begin + length);
	} else {
	  dst.put(bytes, 0, length);
	}
  }

  /**
   * Convert string to ASSCII chars (byte) with null
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param length
   *          the length of destination buffer that be used to save converted
   *          chars.
   */
  public static void stringToNullTerminatedChars(String src, ByteBuffer dst,
	  int length) {
	int begin = dst.position();
	int srcLength = src.length();
	if (srcLength >= length) {
	  srcLength = length - 1;
	}
	for (int i = 0; i < srcLength; i++) {
	  dst.put((byte) src.charAt(i));
	}
	// dst.put((byte)0);
	dst.position(begin + length);
  }

  /**
   * Convert string to bytes by US-ASCII encoding.
   * 
   * @param str
   *          the string that needs to been converted
   * 
   * 
   * @return a array of bytes represents the string
   */
  public static byte[] stringToChars(String str) {
	byte[] result = new byte[str.length()];
	for (int i = 0; i < result.length; i++) {
	  result[i] = (byte) str.charAt(i);
	}
	return result;
  }

  /**
   * Convert string to bytes by US-ASCII encoding.
   * 
   * @param str
   *          the string that needs to been converted
   * 
   * 
   * @return a array of bytes represents the string
   */
  public static void stringToChars(String str, byte[] dst) {
	stringToChars(str, dst, 0, dst.length);
  }

  /**
   * Convert string to null-terminated char (byte) array.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param length
   *          the length of destination buffer that be used to save converted
   *          chars.
   */
  public static void stringToChars(String src, byte[] dst, int length) {
	stringToChars(src, dst, 0, length);
  }

  /**
   * Convert string to chars by US-ASCII encoding.
   * 
   * @param src
   *          the string that needs to been converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param length
   *          the length of destination buffer that be used to save converted
   *          chars.
   * 
   */
  public static void stringToChars(String src, ByteBuffer dst, int length) {
	int begin = dst.position();

	int srclen = src.length();
	if (srclen > length) {
	  srclen = length;
	}
	for (int i = 0; i < srclen; i++) {
	  dst.put((byte) src.charAt(i));
	}
	// dst.put((byte)0);
	dst.position(begin + length);
  }

  /**
   * Convert string to null-terminated char (byte) array.
   * 
   * @param src
   *          the source string to be converted
   * @param dst
   *          the destination buffer to save converted chars
   * @param dstOffset
   *          the beginning position of destination buffer to save converted
   *          chars
   * @param length
   *          the size of destination buffer that can be used to save converted
   *          chars.
   */
  public static void stringToChars(String src, byte[] dst, int dstOffset,
	  int length) {
	int srclen = src.length();
	if (srclen > length) {
	  srclen = length;
	}
	for (int i = 0; i < srclen; i++) {
	  dst[dstOffset + i] = (byte) src.charAt(i);
	}
  }

  /**
   * Converts an array of bytes to short. If the array contains more bytes than
   * fits in a short, only those bytes that would comprise the short are
   * converted.
   * 
   * @param binaryValues
   *          The bytes that needs to be converted.
   * 
   * @returns A short representing the bytes.
   */
  public static short bytesToShort(byte[] bytes) {
	// final int BYTE_SCOPE = 256;

	// This will be result
	int value = 0;

	int unsignedByte = 0;

	for (int i = 0; i < _BYTES_IN_SHORT; i++) {
	  unsignedByte = byteToUnsignedByte(bytes[i]);

	  value = (value << _BITS_IN_BYTE) | unsignedByte;
	}

	return new Integer(value).shortValue();
  }

  /**
   * Converts a long into a byte array with the most significant bit unused for
   * value. The most significant bit of the least significant byte is always
   * zero whereas the most significant bit of other bytes are always one. For
   * example, 3 will be converted into [03], 255 will be converted into
   * [81][7F], 32768 is [82][80][00].
   * 
   * @param number
   *          The long number that needs to be converted.
   * 
   * @return An array of bytes representing the long.
   */
  public static byte[] longToSignedBytes(long number) {
	int numOfBytes = 1;

	final byte MAX_VALUE_PER_BYTE = 0x7F;

	// Calculate the length of the byte array
	long remainder = number / (MAX_VALUE_PER_BYTE + 1);
	while (remainder > 0) {
	  ++numOfBytes;
	  remainder = remainder / (MAX_VALUE_PER_BYTE + 1);
	}

	byte[] binaryValues = new byte[numOfBytes];

	remainder = number;
	for (int i = numOfBytes - 1; i >= 0; --i) {
	  binaryValues[i] = (new Long(remainder % (MAX_VALUE_PER_BYTE + 1))).byteValue();

	  // Sets the most significant bit if this is not the least
	  // significant byte
	  if (i != numOfBytes - 1) {
		binaryValues[i] = (byte) (binaryValues[i] | _SIGN_MASK);
	  }

	  remainder = remainder / (MAX_VALUE_PER_BYTE + 1);
	}

	return binaryValues;
  }

  /**
   * Converts an long into a binary array
   * 
   * @param number
   *          the long number that needs to be converted.
   * 
   * @return The byte representation of the long.
   */
  public static byte[] longToBytes(long number) {
	byte[] binaryValues = new byte[_BYTES_IN_LONG];

	final byte BYTE_MASK = (byte) 0xff;

	for (int i = 0; i < _BYTES_IN_LONG; i++) {
	  // Shift to get each of four bytes of the integer
	  binaryValues[i] = (byte) ((number >> (_BITS_IN_BYTE * (_BYTES_IN_LONG - i - 1))) & BYTE_MASK);
	}

	return binaryValues;
  }

  /**
   * Converts the time in seconds from January 12pm, 1970 into a 4-byte binary
   * array.
   * 
   * 
   * @param secondsInTime
   *          the time in seconds from January 12pm, 1970
   * 
   * @return The byte representation of the time
   */
  public static byte[] longToTimeBytes(long secondsInTime) {
	byte[] longBytes = longToBytes(secondsInTime);

	final int NUM_OF_BYTES_FOR_TIME = 4;

	byte[] timeBytes = new byte[NUM_OF_BYTES_FOR_TIME];

	// Only takes the lower 4 bytes
	for (int i = 0; i < NUM_OF_BYTES_FOR_TIME; ++i) {
	  timeBytes[i] = longBytes[_BYTES_IN_LONG - NUM_OF_BYTES_FOR_TIME + i];
	}

	return timeBytes;
  }

  /**
   * Converts an array of bytes into a version number format
   * (Major/Minor/SQA/Patch). Example: 0.0.1230.1234
   * 
   * @param binaryValue
   *          The array that needs to be converted.
   * 
   * @returns A string representing the version number
   */
  public static String formatVersionNumber(byte[] binaryValue) {
	final String SEPARATOR = ".";
	final int MAJOR_MINOR_VER_BYTES = 2;

	// Use StringBuffer because it's faster
	StringBuffer buffer = new StringBuffer();

	try {
	  // binaryValue should be 6 bytes
	  for (int i = 0; i < MAJOR_MINOR_VER_BYTES; i++) {
		byte[] value = { binaryValue[i] };
		buffer.append(bytesToInt(value));
		buffer.append(SEPARATOR);
	  }

	  // SQA & Patch version are of size 2 bytes each.
	  byte[] sqaVersion = { binaryValue[2], binaryValue[3] };
	  buffer.append(bytesToInt(sqaVersion));
	  buffer.append(SEPARATOR);
	  byte[] patchVersion = { binaryValue[4], binaryValue[5] };
	  buffer.append(bytesToInt(patchVersion));
	} catch (ArrayIndexOutOfBoundsException exception) {
	  System.err.println("ArrayIndexOutOfBoundsException while "
		                 + "formatting the version number");
	}

	// and finally give the version number
	return buffer.toString();
  }

  /**
   * Convert an exact size into a more readable KB, MB, GB, or TB
   * 
   * @param exactSize
   *          the exact size in bytes or bits
   * 
   * @return a string of KB, MB, etc to be more readable
   */
  public static String convertToStringFrom(double exactSize, int decimalDigits) {
	String size = "";

	if (exactSize < KILO) {
	  size = String.valueOf(exactSize);
	} else if (exactSize < MEGA) {
	  size = getValueStringWithDecimals(exactSize / KILO, decimalDigits) + "K";
	} else if (exactSize < GIGA) {
	  size = getValueStringWithDecimals(exactSize / MEGA, decimalDigits) + "M";
	} else if (exactSize < TERA) {
	  size = getValueStringWithDecimals(exactSize / GIGA, decimalDigits) + "G";
	} else if (exactSize >= TERA) {
	  size = getValueStringWithDecimals(exactSize / TERA, decimalDigits) + "T";
	}

	return size;
  }

  /**
   * Return a String of a <code>double</code> number with a specified number of
   * decimal digits.
   * 
   * @param original
   *          original number that will be converted.
   * @param decimalDigits
   *          number of decimal digits that the result will contain.
   */
  public static String getValueStringWithDecimals(double original,
	  int decimalDigits) {
	String valueString = String.valueOf(original);

	String subString = valueString.substring(
	    0,
	    Math.min(valueString.indexOf(".") + decimalDigits, valueString.length()));

	return subString;
  }

  public static int byteToUnsignedByte(byte signedByte) {
	int result = signedByte;

	if (signedByte < 0) {
	  result = 256 + signedByte;
	}

	return result;
  }

  /**
   * Format bytes to hex string like bellow:
   * 
   * <pre>
   * 00070001 00000003 00000000 00000000 00000000 00000000 616C6C00 01000100 
   * 00000400 00000000 00000000 00000000 00000072 6F6F7400 02000100 00000800 
   * 00000000 00000000 00000000 0000002A 696B2C39 6F6C2E00 03000000 00000400 
   * 00000000 00000000 00000000 00000000 000004
   * </pre>
   * 
   * @param bytes
   *          the bytes to format
   * @param offset
   *          the index of the first byte to format
   * @param length
   *          the number of bytes to format
   */
  public static String formatBytes(byte[] bytes, int offset, int length) {

	StringBuilder buf = new StringBuilder(length * 3);
	int index = 0;
	while (index < length) {
	  if (index + _BYTES_IN_INT >= length) {
		binaryToHex(buf, bytes, null, (offset + index), (length - index));
		index = length;
	  } else {
		binaryToHex(buf, bytes, null, (offset + index), _BYTES_IN_INT);
		index += _BYTES_IN_INT;

		if (index % 32 == 0) {
		  buf.append('\n');
		} else {
		  buf.append(' ');
		}
	  }
	}

	return buf.toString();
  }

  public static boolean isValidIpv6Address(String ipAddress) {
	return IPAddressUtil.isIPv6LiteralAddress(ipAddress);
  }

  /**
   * Returns <code>true</code> if the argument, a Unicode code point, is valid
   * in XML documents. Unicode characters fit into the low sixteen bits of a
   * Unicode code point, and pairs of Unicode <em>surrogate characters</em> can
   * be combined to encode Unicode code point in documents containing only
   * Unicode. (The <code>char</code> datatype in the Java Programming Language
   * represents Unicode characters, including unpaired surrogates.) <par> [2]
   * Char ::= #x0009 | #x000A | #x000D | [#x0020-#xD7FF] | [#xE000-#xFFFD] |
   * [#x10000-#x10ffff] </par>
   * 
   * @param code
   *          the 32-bit Unicode code point being tested
   * @return <code>true</code> if the Unicode code point is valid,
   *         <code>false</code> otherwise
   */
  public static final boolean isValidXmlCharCode(int code) {
	return (0x0020 <= code && code <= 0xD7FF) || (0x000A == code)
	       || (0x0009 == code) || (0x000D == code)
	       || (0xE000 <= code && code <= 0xFFFD)
	       || (0x10000 <= code && code <= 0x10ffff);
  }

  /**
   * This method ensures that the output String has only valid XML unicode
   * characters as specified by the XML 1.0 standard. For reference, please see
   * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
   * standard</a>. This method will return an empty String if the input is null
   * or empty.
   * 
   * @param in
   *          The String whose non-valid characters we want to remove.
   * @return The in String, stripped of non-valid characters.
   */
  public static final String stripInvalidXMLCharacters(String in) {
	if (in == null || ("".equals(in)))
	  return ""; // vacancy test.
	
	// Used to hold the output.
	StringBuilder out = new StringBuilder();
	// Used to reference the current character.
	int current;
	for (int i = 0; i < in.length(); i++) {
	  // NOTE: No IndexOutOfBoundsException caught here;
	  // it should not happen.
	  current = in.charAt(i);
	  if ((current == 0x9) || (current == 0xA) || (current == 0xD)
		  || ((current >= 0x20) && (current <= 0xD7FF))
		  || ((current >= 0xE000) && (current <= 0xFFFD))
		  || ((current >= 0x10000) && (current <= 0x10FFFF)))
		out.append((char)current);
	}
	return out.toString();
  }

  /**
   * The <code>parseIpAddress</code> Utility provides a parsing mechanism for
   * parsing the IP Address in dotted Decimal format (%.%.%.%). If an IP Address
   * is Valid then it will return a true else it will return a false.
   * 
   * @param ipAddress
   *          The IP Address that needs to be parsed
   * 
   * @return ipAddress returns true if the IP Address is Valid.
   */
  public static boolean isValidIpv4Address(String ipAddress) {
	return IPAddressUtil.isIPv4LiteralAddress(ipAddress);
	// The Number of Tokens in an IP Address is 4
	// int IP_ADDRESS_TOKEN_COUNT = 4;
	//
	// // The Number of Dots in an IP Address (seperators) is 3
	// int DOTS_IN_IP_ADDRESS = 3;
	//
	// // Minimum Value an IP address Token can have is 0
	// int MIN_VALUE_OF_IP_ADDRESS_TOKEN = 0;
	//
	// // Maximum Value an IP address Token can have is 255
	// int MAX_VALUE_OF_IP_ADDRESS_TOKEN = 255;
	//
	// // Get all the Characters in the Character Array
	// char[] ipAddressCharacterArray = ipAddress.toCharArray();
	//
	// // Put the IP Address in a String Tokenizer
	// StringTokenizer stringValue = new StringTokenizer(ipAddress, ".");
	//
	// // Final check to see if the IP Address is Valid
	// boolean isIpAddressValid = false;
	//
	// // Set the Valid Token Count as 0 and each valid token will increment
	// // the count and each invalid token will decrement the count
	// int validTokenCount = 0;
	//
	// if (stringValue.countTokens() == IP_ADDRESS_TOKEN_COUNT) {
	// // Ok, Tokens are less than the IP Address Token Count
	// // Go in the while loop till we have have more Tokens
	// while (stringValue.hasMoreTokens()) {
	// try {
	// // Get the Integer Value of the token
	// int tokenValue = Integer.parseInt((String) stringValue.nextElement());
	//
	// // Check to see is the token Value is in the Valid Range
	// // where the Minimum Value Range is 0 and the maximum 255
	// if (tokenValue >= MIN_VALUE_OF_IP_ADDRESS_TOKEN
	// && tokenValue <= MAX_VALUE_OF_IP_ADDRESS_TOKEN) {
	// // Tokens are Valid and in the Valid Range
	// validTokenCount++;
	// } else {
	// // No, the Tokens are not in the Valid Range
	// validTokenCount--;
	// }
	// } catch (Exception exception) {
	// validTokenCount--;
	// }
	// }
	// }
	//
	// // Make a final check
	// if (validTokenCount == IP_ADDRESS_TOKEN_COUNT) {
	// // StringTokenizer token = new StringTokenizer(ipAddress, ".");
	//
	// // Ok, all the checks are ok
	//
	// int dotCounter = 0;
	//
	// // Check for number of dots/seperators in the IP Address
	// for (int index = 0; index < ipAddressCharacterArray.length; index++) {
	// String characterString = String.valueOf(ipAddressCharacterArray[index]);
	//
	// // check if the Character is a dot/seperator
	// if (characterString.equals(".")) {
	// // Yes, the Character is a dot/seperator, increment the
	// // Dot-Counter
	// dotCounter++;
	// }
	// }
	//
	// // Check if the Number of Dots/Seperators is equal to the
	// // valid number of dots/Seperators in the IP ADDRESS
	// if (dotCounter == DOTS_IN_IP_ADDRESS) {
	// // Yes, they are, The IP Address seems to be valid
	// isIpAddressValid = true;
	// } else {
	// // No, the IP Address is In Valid
	// isIpAddressValid = false;
	// }
	// } else {
	// // No, the IP address is In Valid
	// isIpAddressValid = false;
	// }
	//
	// return isIpAddressValid;
  }

  public static String formatTime(long seconds) {
	long totalMinutes = seconds / 60;
	long second = seconds % 60;
	long totalHours = totalMinutes / 60;
	long minute = totalMinutes % 60;
	long totalDays = totalHours / 24;
	long hour = totalHours % 24;
	NumberFormat numberFormat = NumberFormat.getInstance();
	numberFormat.setMinimumIntegerDigits(2);
	StringBuffer result = new StringBuffer();
	result.append(String.valueOf(totalDays));
	result.append(" days ");
	result.append(numberFormat.format(hour));
	result.append("h:");
	result.append(numberFormat.format(minute));
	result.append("m:");
	result.append(numberFormat.format(second));
	result.append("s");
	return result.toString();
  }

  /**
   * find
   * 
   * @param bytes
   * @param offset
   * @param length
   * @return
   */
  private final static int findValidCharLength(byte[] bytes, int offset,
	  int length) {
	for (int i = offset + length - 1; i >= offset; i--) {
	  if (bytes[i] != _NULL_TERMINAL_CHAR) {
		return i + 1 - offset;
	  }
	}
	return 0;
  }

  /**
   * Size of Kilo
   */
  public static final long KILO = 1024;

  /**
   * Size of Mega
   */
  public static final long MEGA = 1048576;

  /**
   * Size of Giga
   */
  public static final long GIGA = 1073741824;

  /**
   * Size of Tera
   */
  public static final long TERA = 1099511627776L;

  //
  // PRIVATE
  //

  /** Mask used to keep upper nibble and clear lower nibble */
  // private static final int UPPER_NIBBLE_MASK = 0xf0;
  /** Mask used to keep lower nibble and clear upper nibble */
  private static final int LOWER_NIBBLE_MASK = 0x0f;

  /** Number of shifts required to move upper nibble to lower nibble */
  private static final int NIBBLE_SHIFT = 4;

  private static final int _SIGN_MASK = 0x80;

  /** Number of bits in a byte */
  private static final int _BITS_IN_BYTE = 8;

  /** Number of bytes in a short */
  private static final int _BYTES_IN_SHORT = 2;

  /** Number of bytes in a long */
  private static final int _BYTES_IN_LONG = 8;

  /** Number of bytes in an int */
  private static final int _BYTES_IN_INT = 4;

  private static final byte _NULL_TERMINAL_CHAR = (byte) 0;

  /** The separator to use to separate the byte components of a IP address */
  public final static String _IP_SEPARATOR = ".";

  private final static char[] _DIGIT_CHARS = { '0', '1', '2', '3', '4', '5',
	  '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

}
