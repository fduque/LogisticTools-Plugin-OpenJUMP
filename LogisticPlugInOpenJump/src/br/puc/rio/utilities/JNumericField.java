package br.puc.rio.utilities;
  import java.awt.Color;
    import javax.swing.JTextField;
    import javax.swing.text.*;
    
    public class JNumericField extends JTextField {
    	
    	/*
    	 * Essa classe cria um componente para formulário TextField personalizado onde só é possível entrar com tipos numéricos.
    	 */	
    	
    	private static final long serialVersionUID = 1L;
    	
    	private static final char DOT = '.';
    	private static final char NEGATIVE = '-';
    	private static final String BLANK = "";
    	private static final int DEF_PRECISION = 2;
    
    	public static final int NUMERIC = 2;
    	public static final int DECIMAL = 3;
    
    	public static final String FM_NUMERIC = "0123456789";
    	public static final String FM_DECIMAL = FM_NUMERIC + DOT; 
    
    	private int maxLength = 0;
    	private int format = NUMERIC;
    	private String negativeChars = BLANK;
    	private String allowedChars = null;
    	private boolean allowNegative = false;
    	private int precision = 0;
    
    	protected PlainDocument numberFieldFilter;
    
    	public JNumericField() {
    
    		this(2, DECIMAL);
    	}
    
    	public JNumericField(int iMaxLen) {
    		this(iMaxLen, NUMERIC);
    	}
    
    	public JNumericField(int iMaxLen, int iFormat) {
    		setAllowNegative(true);
    		setMaxLength(iMaxLen);
    		setFormat(iFormat);
    
    		numberFieldFilter = new JNumberFieldFilter();
    		super.setDocument(numberFieldFilter);
    	}
    
    	public void setMaxLength(int maxLen) {
    		if (maxLen > 0)
    			maxLength = maxLen;
    		else
    			maxLength = 0;
    	}
    
    	public int getMaxLength() {
    		return maxLength;
    	}
    
    	public void setEnabled(boolean enable) {
    		super.setEnabled(enable);
    
    		if (enable) {
    			setBackground(Color.white);
    			setForeground(Color.black);
    		} else {
    			setBackground(Color.lightGray);
    			setForeground(Color.darkGray);
    		}
    	}
    
    	public void setEditable(boolean enable) {
    		super.setEditable(enable);
    
    		if (enable) {
    			setBackground(Color.white);
    			setForeground(Color.black);
    		} else {
    			setBackground(Color.lightGray);
    			setForeground(Color.darkGray);
    		}
    	}
    
    	public void setPrecision(int iPrecision) {
    		if (format == NUMERIC)
    			return;
    
    		if (iPrecision >= 0)
    			precision = iPrecision;
    		else
    			precision = DEF_PRECISION;
    	}
    
    	public int getPrecision() {
    		return precision;
    	}
    
    	public Number getNumber() {
    		Number number = null;
    		if (format == NUMERIC)
    			number = new Integer(getText());
    		else
    			number = new Double(getText());
    
    		return number;
    	}
    
    	public void setNumber(Number value) {
    		setText(String.valueOf(value));
    	}
    
    	public int getInt() {
    		return Integer.parseInt(getText());
    	}
    
    	public void setInt(int value) {
    		setText(String.valueOf(value));
    	}
    
    	public float getFloat() {
    		return (new Float(getText())).floatValue();
    	}
    
    	public void setFloat(float value) {
    		setText(String.valueOf(value));
    	}
    
    	public double getDouble() {
    		return (new Double(getText())).doubleValue();
    	}
    
    	public void setDouble(double value) {
    		setText(String.valueOf(value));
    	}
    
    	public int getFormat() {
    		return format;
    	}
    
    	public void setFormat(int iFormat) {
    		switch (iFormat) {
    		case NUMERIC:
    		default:
    			format = NUMERIC;
    			precision = 0;
    			allowedChars = FM_NUMERIC;
    			break;
    
    		case DECIMAL:
    			format = DECIMAL;
    			precision = DEF_PRECISION;
    			allowedChars = FM_DECIMAL;
    			break;
    		}
    	}
    
    	public void setAllowNegative(boolean b) {
    		allowNegative = b;
    
    		if (b)
    			negativeChars = "" + NEGATIVE;
    		else
    			negativeChars = BLANK;
    	}
    
    	public boolean isAllowNegative() {
    		return allowNegative;
    	}
    
    	public void setDocument(Document document) {
    	}
    
    	class JNumberFieldFilter extends PlainDocument {
    		private static final long serialVersionUID = 1L;
    
    		public JNumberFieldFilter() {
    			super();
    		}
    
    		public void insertString(int offset, String str, AttributeSet attr)
    				throws BadLocationException {
    			String text = getText(0, offset) + str
    					+ getText(offset, (getLength() - offset));
    
    			if (str == null || text == null)
    				return;
    
    			for (int i = 0; i < str.length(); i++) {
    				if ((allowedChars + negativeChars).indexOf(str.charAt(i)) == -1)
    					return;
    			}
    
    			int precisionLength = 0, dotLength = 0, minusLength = 0;
    			int textLength = text.length();
    
    			try {
    				if (format == NUMERIC) {
    					if (!((text.equals(negativeChars)) && (text.length() == 1)))
    						new Long(text);
    				} else if (format == DECIMAL) {
    					if (!((text.equals(negativeChars)) && (text.length() == 1))) 
    						new Double(text);
    
    					int dotIndex = text.indexOf(DOT);
    					
    					if (dotIndex != -1) {
    						dotLength = 1;
    						precisionLength = textLength - dotIndex - dotLength;
    
    						if (precisionLength > precision)
    							return;
    					}
    				}
    			} catch (Exception ex) {
    				return;
    			}
    
    			if (text.startsWith("" + NEGATIVE)) {
    				if (!allowNegative)
    					return;
    				else
    					minusLength = 1;
    			}
    
    			if (maxLength < (textLength - dotLength - precisionLength - minusLength))
    				return;
    
    			super.insertString(offset, str, attr);
    		}
    	}
    }


 
