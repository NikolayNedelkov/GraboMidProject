package dataclasses;
import java.time.LocalDate;


//User write comments on DestinationTraders walls
public class Comment {

	private User user;
	private String info;
	private double rating;
	private LocalDate date;
	private DestinationTrader destination;

	public Comment(User user, DestinationTrader destination, String info, double rating) {
		setUser(user);
		setInfo(info);
		setRating(rating);
		this.date = LocalDate.now();
		setDestination(destination);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if (user != null)
			this.user = user;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		if (rating > 0 && rating < 6) {
			this.rating = rating;
		}
	}

	public LocalDate getDate() {
		return this.date;
	}

	public DestinationTrader getDestination() {
		return destination;
	}

	public void setDestination(DestinationTrader destination) {
		if (destination != null)
			this.destination = destination;
	}

	@Override
	public String toString() {
		return "Comment [user=" + user + ", info=" + info + ", rating=" + rating + ", date=" + date + ", destination="
				+ destination + "]";
	}
	
}
