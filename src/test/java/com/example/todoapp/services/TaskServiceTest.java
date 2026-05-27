package com.example.todoapp.services;

import com.example.todoapp.exceptions.ResourceNotFoundException;
import com.example.todoapp.models.Task;
import com.example.todoapp.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createNewTask_savesAndReturnsTask() {
        // Arrange
        Task input = new Task("Buy milk", false);
        Task saved = new Task("Buy milk", false);
        saved.setId(1L);

        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        // Act
        Task result = taskService.createNewTask(input);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Buy milk", result.getTask());
        verify(taskRepository).save(input);
    }

    @Test
    void deleteTask_whenMissing_throwsNotFound() {
        // Arrange
        when(taskRepository.existsById(99L)).thenReturn(false);

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> taskService.deleteTask(99L));

        verify(taskRepository, never()).deleteById(any());
    }
}