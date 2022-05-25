package com.gadarts.necromine.model;

public interface ElementDefinition {
	String getDisplayName( );

	String name( );

	default boolean isCanBeSeenOnTheMap( ) {
		return true;
	}

	int ordinal( );
}
