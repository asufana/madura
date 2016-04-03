package com.github.asufana.madura.lexer;

import com.github.asufana.madura.token.Token;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.io.Reader;

public class LexerServiceTest {

    @Ignore
    @Test
    public void test() throws ParseException {
        LexerService lexer = new LexerService(new CodeDialog());
        for (Token token; (token = lexer.read()) != Token.EOF;) {
            System.out.println("=> " + token.getText());
        }
    }
    
    public static class CodeDialog extends Reader {
        
        private String buffer = null;
        private int pos = 0;
        
        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (buffer == null) {
                String in = showDialog();
                if (in == null) {
                    return -1;
                }
                else {
                    System.out.println(in);
                    buffer = in + "\n";
                    pos = 0;
                }
            }
            
            int size = 0;
            int length = buffer.length();
            while (pos < length && size < len) {
                cbuf[off + size++] = buffer.charAt(pos++);
            }
            if (pos == length) {
                buffer = null;
            }
            return size;
        }
        
        protected String showDialog() {
            JTextArea area = new JTextArea(20, 40);
            JScrollPane pane = new JScrollPane(area);
            int result = JOptionPane.showOptionDialog(null,
                                                      pane,
                                                      "Input",
                                                      JOptionPane.OK_CANCEL_OPTION,
                                                      JOptionPane.PLAIN_MESSAGE,
                                                      null,
                                                      null,
                                                      null);
            if (result == JOptionPane.OK_OPTION) {
                return area.getText();
            }
            else {
                return null;
            }
        }
        
        @Override
        public void close() throws IOException {}
    }
}
