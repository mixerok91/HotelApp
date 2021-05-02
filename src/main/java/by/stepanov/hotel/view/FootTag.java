package by.stepanov.hotel.view;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FootTag extends TagSupport {

    private static final String TEXT = "<p>Made by Max Stepanov</p>";
    private static final String START_TAG = "<div class=\"foot\">";
    private static final String END_TAG = "</div>";

    private static final Logger log = Logger.getLogger(FootTag.class);

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.write(START_TAG);
            writer.write(TEXT);
        } catch (IOException e) {
            log.error(e);
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.write(END_TAG);
        } catch (IOException e) {
            log.error(e);
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
}
