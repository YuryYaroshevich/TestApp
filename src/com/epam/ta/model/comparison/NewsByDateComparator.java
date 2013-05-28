package com.epam.ta.model.comparison;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import com.epam.ta.model.News;
import com.epam.ta.util.datelocalizator.DateLocalizator;

public class NewsByDateComparator implements Comparator<News>, Serializable {
	public int compare(News news1, News news2) {
		Date date1 = DateLocalizator.
				parseUSDate(news1.getDateOfPublishing());
		Date date2 = DateLocalizator.
				parseUSDate(news2.getDateOfPublishing());
		// changes sign of return value to opposite for outputting in descending
		// order
		return -date1.compareTo(date2);
	}
}
