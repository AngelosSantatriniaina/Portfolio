package mg.portfolio.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_name", nullable = false, length = 150)
    private String authorName;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private boolean published = true;

    protected Review() {}

    public Review(String authorName, String content, int rating, boolean published) {
        update(authorName, content, rating, published);
    }

    public Long getId() { return id; }
    public String getAuthorName() { return authorName; }
    public String getContent() { return content; }
    public int getRating() { return rating; }
    public boolean isPublished() { return published; }

    public void update(String authorName, String content, int rating, boolean published) {
        this.authorName = authorName;
        this.content = content;
        this.rating = rating;
        this.published = published;
    }
}
