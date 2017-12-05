package cu.cs.cpsc215.project2;

import javax.swing.JTable;
/**
 * Class to do an exact search over the library
 * @author Caroline
 *
 */
public class Search implements StrategySearch {

	@Override
	public boolean search(Book book, String usersearch) {
		System.out.println(book.getGenre());
		if(book.getTitle().contains(usersearch) || book.getAuthor().contains(usersearch) || book.getGenre().contains(usersearch)) {
			return true;
		}
		for (String it : book.getTags()) {
			if(it.contains(usersearch))
				return true;
		}	
		
		return false;
	}
}
