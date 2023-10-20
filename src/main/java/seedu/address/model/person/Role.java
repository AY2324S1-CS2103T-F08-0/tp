package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoleType(String)}
 */
public class Role {

    /**
     * Describes the type of the Role. The user input / output must match these values
     * in the code exactly.
     */
    public enum RoleType {
        Student,
        TA,
        Professor
    }

    public static final String MESSAGE_CONSTRAINTS = "Roles must take one of the roleTypes: Student, TA, or Professor.";

    /*
     * The Role must be either one of Student, TA or Professor.
     */
    public static final String VALIDATION_REGEX = "^(Student|TA|Professor)(, (Student|TA|Professor))*$";
    public final Set<RoleType> roleType;


    /**
     * Constructs a {@code Role}.
     *
     * @param roleString A valid role string.
     */
    public Role(String roleString) {
        requireNonNull(roleString);
        checkArgument(isValidRoleType(roleString), MESSAGE_CONSTRAINTS);
        roleType = new HashSet<>();
        String[] roleTypeStrings = roleString.split(",");
        for (String roleTypeStr : roleTypeStrings) {
            roleType.add(RoleType.valueOf(roleTypeStr.trim()));
        }
    }

    public Set<RoleType> getRoleType() {
        return roleType;
    }

    public String getRoleTypesAsString() {
        return roleType.stream().map(Enum::toString).collect(Collectors.joining(", "));
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRoleType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return getRoleTypesAsString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleType.equals(otherRole.roleType);
    }

    @Override
    public int hashCode() {
        return roleType.hashCode();
    }
}
