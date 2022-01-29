import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.*;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.*;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.*;

import java.awt.event.KeyEvent;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

public class Baengbot{
  Robot robot = new Robot();
  String pdfContent;
  String cfgBegin;
  String cfgDelayPress;
  String cfgDelayRelease;
  
  // Constructor
  public Baengbot() throws AWTException, IOException, TikaException {
    cfgBegin        = Config.begin();
    cfgDelayPress   = Config.delayPress();
    cfgDelayRelease = Config.delayRelease();
    return;
  }

  public void run(String pdfPath) {

  }
}
