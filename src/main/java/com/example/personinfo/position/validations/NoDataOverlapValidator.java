package com.example.personinfo.position.validations;

import com.example.personinfo.position.commands.CreatePositionCommand;
import com.example.personinfo.position.commands.UpdatePositionCommand;
import com.example.personinfo.position.models.Position;
import com.example.personinfo.position.repositories.PositionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class NoDataOverlapValidator implements ConstraintValidator<NoDateOverlap,Object> {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void initialize(NoDateOverlap constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value instanceof CreatePositionCommand) {
            return validateCreatePositionCommand((CreatePositionCommand) value);
        } else if (value instanceof UpdatePositionCommand) {
            return validateUpdatePositionCommand((UpdatePositionCommand) value);
        }
        return true;
    }

    private boolean validateCreatePositionCommand(CreatePositionCommand command) {
        LocalDate start = command.getStartDate();
        LocalDate end = command.getEndDate();
        return checkDateOverlap(start, end);
    }

    private boolean validateUpdatePositionCommand(UpdatePositionCommand command) {
        LocalDate start = command.getStartDate();
        LocalDate end = command.getEndDate();
        return checkDateOverlap(start, end);
    }

    private boolean checkDateOverlap(LocalDate start, LocalDate end) {
        List<Position> positions = positionRepository.findAll();
        for (Position position : positions) {
            if ((start != null && start.isBefore(position.getEndDate())) &&
                    (end != null && end.isAfter(position.getStartDate()))) {
                return false;
            }
        }
        return true;
    }
}
