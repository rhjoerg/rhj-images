package ch.rhj.images.svg.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonTypeName("text")
public class Text {

	@JacksonXmlProperty(isAttribute = true)
	public double x;
	@JacksonXmlProperty(isAttribute = true)
	public double y;

	@JacksonXmlText
	public String text;

	@JacksonXmlProperty(isAttribute = true)
	public String style;

	protected Text() {
	}

	public Text(double x, double y, String text) {

		this.x = x;
		this.y = y;
		this.text = text;
	}
}
