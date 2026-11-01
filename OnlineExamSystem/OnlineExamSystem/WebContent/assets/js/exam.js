// Exam Timer Functionality
document.addEventListener('DOMContentLoaded', function() {
    const EXAM_DURATION = 60 * 60; // 60 minutes in seconds
    const timerElement = document.getElementById('timer');
    const examForm = document.getElementById('examForm');

    let timeRemaining = EXAM_DURATION;

    // Initialize timer
    updateTimerDisplay();

    // Start the timer
    const timerInterval = setInterval(function() {
        timeRemaining--;
        updateTimerDisplay();

        // Auto-submit when time is over
        if (timeRemaining <= 0) {
            clearInterval(timerInterval);
            alert('Time is over! Your exam will be submitted automatically.');
            examForm.submit();
        }

        // Warning at 5 minutes
        if (timeRemaining === 5 * 60) {
            alert('Warning: Only 5 minutes remaining!');
        }
    }, 1000);

    // Update timer display
    function updateTimerDisplay() {
        const minutes = Math.floor(timeRemaining / 60);
        const seconds = timeRemaining % 60;

        const displayMinutes = String(minutes).padStart(2, '0');
        const displaySeconds = String(seconds).padStart(2, '0');

        timerElement.textContent = displayMinutes + ':' + displaySeconds;

        // Change color when time is running out
        if (timeRemaining <= 5 * 60) {
            timerElement.style.color = '#dc3545';
        }
        if (timeRemaining <= 60) {
            timerElement.style.color = '#ff0000';
        }
    }

    // Prevent accidental page unload
    window.addEventListener('beforeunload', function(e) {
        if (timeRemaining > 0) {
            e.preventDefault();
            e.returnValue = '';
            return '';
        }
    });

    // Clear all answers
    document.querySelector('.btn-secondary').addEventListener('click', function(e) {
        if (!confirm('Are you sure you want to clear all your answers?')) {
            e.preventDefault();
        }
    });
});
