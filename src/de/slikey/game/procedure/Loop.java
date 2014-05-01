package de.slikey.game.procedure;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;

public class Loop implements Runnable {

	protected final Map<Element, Condition> elements;
	private int ticks;
	private boolean paused;

	public Loop() {
		elements = new HashMap<Element, Condition>();
		ticks = 0;
		paused = false;
	}

	@Override
	public final void run() {
		if (paused)
			return;
		ticks++;
		for (Entry<Element, Condition> entry : elements.entrySet())
			if (entry.getValue().check(ticks))
				entry.getKey().run(ticks);
		onRun();
	}

	protected void onRun() {

	}

	public final void addElement(Element element, Condition condition) {
		Validate.notNull(element, "Element cannot be null!");
		Validate.notNull(condition, "Condition cannot be null!");
		Validate.isTrue(!elements.containsKey(element), "Element is already in loop.");
		Validate.isTrue(!elements.containsValue(condition), "Condition is already in loop.");
		elements.put(element, condition);
	}

	public final void removeCondition(Element element) {
		Validate.notNull(element, "Element cannot be null");
		elements.remove(element);
	}

	public void setPause(boolean paused) {
		this.paused = paused;
	}

}
