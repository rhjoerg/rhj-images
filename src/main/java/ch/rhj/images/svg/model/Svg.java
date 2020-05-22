package ch.rhj.images.svg.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonRootName("svg")
public class Svg {

	@JacksonXmlProperty(isAttribute = true)
	public String xmlns = "http://www.w3.org/2000/svg";

	@JacksonXmlProperty(isAttribute = true)
	public int width;

	@JacksonXmlProperty(isAttribute = true)
	public int height;

	public Rect rect;
	public Text text;

	protected Svg() {
	}

	public Svg(int width, int height) {

		this.width = width;
		this.height = height;
	}
}
