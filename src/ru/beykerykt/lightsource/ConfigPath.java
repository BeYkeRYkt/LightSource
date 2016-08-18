package ru.beykerykt.lightsource;

public class ConfigPath {

	public static class GENERAL {
		public static String ADD_TO_ASYNC_LIGHTING_QUEUE = "add-to-async-lighting-queue";
	}

	public static class UPDATER {
		public static String ENABLE = "updater.enable";
		public static String REPO = "updater.repo";
		public static String UPDATE_DELAY_TICKS = "updater.update-delay-ticks";
		public static String VIEW_CHANGELOG = "updater.view-changelog";
	}

	public static class SOURCES {
		public static String UPDATE_DELAY_TICKS = "sources.update-delay-ticks";

		public static class SEARCH {
			public static String SEARCH_PLAYERS = "sources.search.search-players";
			public static String SEARCH_ENTITIES = "sources.search.search-entities";
			public static String SEARCH_BURNING_ENTITIES = "sources.search.search-burning-entities";
			public static String SEARCH_ITEMS = "sources.search.search-items";
			public static String SEARCH_RADIUS = "sources.search.search-radius";
			public static String SEARCH_DELAY_TICKS = "sources.search.search-delay-ticks";
		}
	}
}
