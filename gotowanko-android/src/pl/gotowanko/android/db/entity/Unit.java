package pl.gotowanko.android.db.entity;

import java.util.Locale;

public enum Unit {
	KG(new String[] { "Kg", "Kilogram" }),
	DAG(new String[] { "Dag", "Dekagram" }),
	G(new String[] { "g", "Gram" }),
	L(new String[] { "l", "Litr" }),
	ML(new String[] { "ml", "mililitr" }),
	SZT(new String[] { "szt.", "sztuk" });

	private String[] stringNames;

	public String[] getStringNames() {
		return stringNames;
	}

	public static Unit fromString(String str) {
		for (Unit u : values()) {
			for (String name : u.getStringNames()) {
				if (name.toLowerCase(Locale.ENGLISH).equals(str.toLowerCase(Locale.ENGLISH)))
					return u;
			}
		}
		throw new IllegalArgumentException(String.format("Such unit(%s) doesn't exists", str));
	}

	Unit(String[] names) {
		this.stringNames = names;
	}

	@Override
	public String toString() {
		return getStringNames()[0];
	}

};