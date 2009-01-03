/*******************************************************************************
 * Copyright (c) 2001, 2008 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/cpl-v10.html
 *
 * Contributors:
 *     Pavel Savara
 *     - Initial implementation
 *******************************************************************************/
package net.sf.robocode.test.robots;


import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobotTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;

import javax.swing.*;


/**
 * @author Pavel Savara (original)
 */
public class TestAwtAttack extends RobotTestBed {
	boolean messagedAttack;
	boolean messagedBreakthru;

	@Test
	public void run() {
		super.run();
	}

	public void onTurnEnded(TurnEndedEvent event) {
		super.onTurnEnded(event);
		final String out = event.getTurnSnapshot().getRobots()[1].getOutputStreamSnapshot();

		if (out.contains("Hacked!!!")) {
			messagedBreakthru = true;
		}
		if (out.contains("Robots are not alowed to reference javax.swing package")) {
			messagedAttack = true;
		}
	}

	@Override
	public String getRobotNames() {
		return "tested.robots.BattleLost,tested.robots.AwtAttack";
	}

	JFrame frame;

	@Override
	protected void runSetup() {
		frame = new JFrame();
		frame.setVisible(true);
	}

	@Override
	protected void runTeardown() {

		Runnable doHack = new Runnable() {
			public void run() {
				System.out.println("works still!!!");
			}
		};

		javax.swing.SwingUtilities.invokeLater(doHack);

		frame.setVisible(false);
		Assert.assertFalse(messagedBreakthru);
		Assert.assertTrue(messagedAttack);
	}
}
