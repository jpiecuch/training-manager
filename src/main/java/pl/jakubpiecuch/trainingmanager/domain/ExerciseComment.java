package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "exercise_comment")
public class ExerciseComment extends BaseComment<Exercise> {}
