package com.example.navigator.navigatormapbuilder.map_builder.io;

import com.example.navigator.navigatormapbuilder.map_builder.utils.common.Disposable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Printer implements Disposable {
	private PrintStream out;


	public Printer(File output) throws FileNotFoundException {
		out = new PrintStream(output);
	}


	public void write(String s){
		out.println(s);
	}


	@Override
	public void dispose() {
		out.close();
		out = null;
	}
}
