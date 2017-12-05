package cu.cs.cpsc215.project2;

import javax.swing.JTable;

/**
 *  Class to do an inexact search over the library
 * @author Caroline
 *
 */
public class FuzzySearch implements StrategySearch {

	@Override
	public boolean search(Book book, String usersearch) {
		
		System.out.println(book.getGenre());
		if(book.getTitle().toLowerCase().contains(usersearch.toLowerCase()) || 
				book.getAuthor().toLowerCase().contains(usersearch.toLowerCase()) || 
					book.getGenre().toLowerCase().contains(usersearch.toLowerCase())) {
			return true;
		}
		for (String it : book.getTags()) {
			if(it.toLowerCase().contains(usersearch.toLowerCase()))
				return true;
		}	
		
		return false;
	}
}
