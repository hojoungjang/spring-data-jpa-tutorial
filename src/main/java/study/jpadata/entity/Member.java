package study.jpadata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(of = {"id", "name", "age"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedQuery(
    name = "Member.findByUsername",
    query = "select m from Member m where m.name = :username"
)
@NamedEntityGraph(
    name = "Member.all",
    attributeNodes = @NamedAttributeNode("team")
)
public class Member extends AuditFields {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeTeam(Team team) {
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
