/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.test;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.loboevolution.html.control.input.JAutoTextField;

public class AutoText {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				List<String> list = new ArrayList<String>(Arrays.asList("Austria", "Croatia", "France", "Italy"));
				JFrame f = new JFrame("AutoTest");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.add(new JAutoTextField(list));
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
			}
		});
	}
}