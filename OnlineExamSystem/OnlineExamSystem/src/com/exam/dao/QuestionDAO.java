package com.exam.dao;

import com.exam.model.Question;
import com.exam.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getQuestionsByExamId(int examId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE exam_id = ? ORDER BY question_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, examId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("question_id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOptionA(rs.getString("option_a"));
                q.setOptionB(rs.getString("option_b"));
                q.setOptionC(rs.getString("option_c"));
                q.setOptionD(rs.getString("option_d"));
                q.setCorrectAnswer(rs.getString("correct_answer"));
                q.setExamId(rs.getInt("exam_id"));
                questions.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public boolean addQuestion(Question question) {
        String query = "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer, exam_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getOptionA());
            pstmt.setString(3, question.getOptionB());
            pstmt.setString(4, question.getOptionC());
            pstmt.setString(5, question.getOptionD());
            pstmt.setString(6, question.getCorrectAnswer());
            pstmt.setInt(7, question.getExamId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteQuestion(int questionId) {
        String query = "DELETE FROM questions WHERE question_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, questionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
