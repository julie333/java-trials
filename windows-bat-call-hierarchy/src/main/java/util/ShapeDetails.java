package util;

/**
 * Class To Define the attributes of the Shapes used in GenerateGraphFile.java
 * 
 * @author Julie George
 */

import lombok.Data;

@Data
public class ShapeDetails {
	public String fontname;
	public double fontsize;
	public String shape;
	public String style;
	public String fillcolor;
	public double width;
	
//	Node
	public ShapeDetails(String fontname, double fontsize) {
		super();
		this.fontname = fontname;
		this.fontsize = fontsize;
	}

//	Edge
	public ShapeDetails(String fontname, double fontsize, String shape, String style, String fillcolor) {
		this(fontname,fontsize);
		this.shape = shape;
		this.style = style;
		this.fillcolor = fillcolor;
	}

//	Start & End Nodes
	public ShapeDetails(String shape, String fillcolor, double width) {
		super();
		this.shape = shape;
		this.fillcolor = fillcolor;
		this.width = width;
	}
}
