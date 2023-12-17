package com.example.personinfo.position.validations;

import com.example.personinfo.position.commands.PositionAssignCommand;
import com.example.personinfo.position.models.Position;
import com.example.personinfo.position.services.PositionService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class NoPositionDateOverlapValidator implements ConstraintValidator<NoPositionDateOverlap, PositionAssignCommand> {

    @Autowired
    private PositionService positionService;

    @Override
    public void initialize(NoPositionDateOverlap constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PositionAssignCommand command, ConstraintValidatorContext constraintValidatorContext) {

        LocalDate start = command.getStartDate();
        LocalDate end = command.getEndDate();

        List<Position> positions = positionService.getAllByEmployeeId(command.getEmployeeId());
        for (Position p : positions) {
            if (start.isBefore(p.getEndDate()) && end.isAfter(p.getStartDate())) {
                return false;
            }
        }

        return true;
    }
}
