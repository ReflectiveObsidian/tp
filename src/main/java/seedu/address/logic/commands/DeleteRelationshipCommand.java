package seedu.address.logic.commands;

import java.util.UUID;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.relationship.Relationship;


/**
 * Represents a command to delete a relationship between two persons.
 */
public class DeleteRelationshipCommand extends Command {
    public static final String COMMAND_WORD = "deleterelation";
    public static final String MESSAGE_DELETE_RELATIONSHIP_SUCCESS = "Delete successful";
    public static final String COMMAND_WORD_SHORT = "dr";
    private String originUuid = "0000";
    private String targetUuid = "0000";
    private String relationshipDescriptor;

    private boolean isRelationType = false;

    /**
     * Constructor for deleteRelationshipCommand, deletes Relationship sepcified by the 2 Person Uuid given if the
     * relationship exist otherwise tell user relationship do not exist
     * @param originUuid First Person UUID of relationship
     * @param targetUuid Second Person UUID of relationship
     * @param relationshipDescriptor String describing the type of relationship if exisiting
     */
    public DeleteRelationshipCommand(String originUuid, String targetUuid, String relationshipDescriptor) {
        this.originUuid = originUuid;
        this.targetUuid = targetUuid;
        this.relationshipDescriptor = relationshipDescriptor.toLowerCase();
    }

    /**
     * Constructor for deleteRelationshipCommand, deletes Relationship type if it exist otherwise tell user relationship
     * type do not exist
     * @param relationshipDescriptor String describing the type of relationship if exisiting
     * @param isRelationType boolean to check if the relationship is a type
     */
    public DeleteRelationshipCommand(String relationshipDescriptor, boolean isRelationType) {
        this.relationshipDescriptor = relationshipDescriptor.toLowerCase();
        this.isRelationType = isRelationType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        UUID fullOriginUuid = model.getFullUuid(originUuid);
        UUID fullTargetUuid = model.getFullUuid(targetUuid);
        if (!isRelationType) {
            if (fullOriginUuid == null || fullTargetUuid == null) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_UUID);
            }
        }
        if (isRelationType) {
            try {
                model.deleteRelationType(relationshipDescriptor);
                return new CommandResult(MESSAGE_DELETE_RELATIONSHIP_SUCCESS);
            } catch (IllegalArgumentException e) {
                throw new CommandException(e.getMessage());
            }
        }
        try {
            Relationship toDelete = new Relationship(fullOriginUuid, fullTargetUuid, relationshipDescriptor);
            if (!model.hasRelationshipWithDescriptor(toDelete)) {
                throw new CommandException(String.format("Sorry %s do not exist", toDelete));
            }
            model.deleteRelationship(toDelete);
            return new CommandResult(MESSAGE_DELETE_RELATIONSHIP_SUCCESS);
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format("Sorry, we do not accept %s as type", relationshipDescriptor));
        }
    }
}


