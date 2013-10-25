package com.lge.auctionsniper.test;

import junit.framework.Assert;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.ViewAssertion;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;
import com.jayway.android.robotium.solo.Solo;

import com.lge.auctionsniper.R;

public class SniperRunner {
	
	private Solo solo;
	private AuctionSniperEndToEndTest inst;

	public SniperRunner(AuctionSniperEndToEndTest inst) {
		this.inst = inst;
	}

	public void join() {
		solo = new Solo(inst.getInstrumentation(), inst.getActivity());
	}

	public void showsJoiningStatus() {
		Espresso.onView(ViewMatchers.withId(R.id.status))
			.check(ViewAssertions.matches(ViewMatchers.withText("Joining")));
	}

	public void showsLostStatus() {
//		Espresso.onView(ViewMatchers.withId(R.id.status))
//		.check(ViewAssertions.matches(ViewMatchers.withText("Lost")));
		Assert.assertTrue(solo.waitForText("Lost"));
	}

}
