package com.liewei.radish_job.async;

public abstract class Callback<T> {
	public void onStart() {
	}

	abstract public void onHandle(T result);

}
