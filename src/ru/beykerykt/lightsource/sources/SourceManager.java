package ru.beykerykt.lightsource.sources;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Entity;

public class SourceManager {

	private List<Source> sources;

	public SourceManager() {
		this.sources = new CopyOnWriteArrayList<Source>();
	}

	public boolean addSource(Source source) {
		if (sources.contains(source))
			return false;
		sources.add(source);
		return true;
	}

	public boolean removeSource(Source source) {
		if (!sources.contains(source))
			return false;
		sources.remove(source);
		return true;
	}

	public Source getSource(Entity entity) {
		for (Source source : getSourceList()) {
			if (source instanceof OwnedSource) {
				OwnedSource os = (OwnedSource) source;
				if (os.getOwner().getUniqueId().equals(entity.getUniqueId())) {
					return source;
				}
			}
		}
		return null;
	}

	public boolean isSource(Entity entity) {
		for (Source source : getSourceList()) {
			if (source instanceof OwnedSource) {
				OwnedSource os = (OwnedSource) source;
				if (os.getOwner().getUniqueId().equals(entity.getUniqueId())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Source> getSourceList() {
		return sources;
	}
}
