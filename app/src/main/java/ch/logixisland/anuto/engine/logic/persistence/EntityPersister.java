package ch.logixisland.anuto.engine.logic.persistence;

import ch.logixisland.anuto.data.GameDescriptor;
import ch.logixisland.anuto.data.entity.EntityDescriptor;
import ch.logixisland.anuto.engine.logic.GameEngine;
import ch.logixisland.anuto.engine.logic.entity.Entity;
import ch.logixisland.anuto.engine.logic.entity.EntityRegistry;
import ch.logixisland.anuto.util.iterator.StreamIterator;

public abstract class EntityPersister implements Persister {

    private final GameEngine mGameEngine;
    private final EntityRegistry mEntityRegistry;
    private final String mEntityName;

    public EntityPersister(GameEngine gameEngine, EntityRegistry entityRegistry, String entityName) {
        mGameEngine = gameEngine;
        mEntityRegistry = entityRegistry;
        mEntityName = entityName;
    }

    @Override
    public void writeDescriptor(GameDescriptor gameDescriptor) {
        StreamIterator<Entity> iterator = mGameEngine.getAllEntities()
                .filter(Entity.nameEquals(mEntityName));

        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            gameDescriptor.addEntityDescriptor(writeEntityDescriptor(entity));
        }
    }

    @Override
    public void readDescriptor(GameDescriptor gameDescriptor) {
        for (EntityDescriptor entityDescriptor : gameDescriptor.getEntityDescriptors()) {
            if (mEntityName.equals(entityDescriptor.getName())) {
                mGameEngine.add(readEntityDescriptor(entityDescriptor));
            }
        }
    }

    protected abstract EntityDescriptor createEntityDescriptor();

    protected EntityDescriptor writeEntityDescriptor(Entity entity) {
        EntityDescriptor entityDescriptor = createEntityDescriptor();

        entityDescriptor.setId(entity.getEntityId());
        entityDescriptor.setName(entity.getEntityName());
        entityDescriptor.setPosition(entity.getPosition());

        return entityDescriptor;
    }

    protected Entity readEntityDescriptor(EntityDescriptor entityDescriptor) {
        Entity entity = mEntityRegistry.createEntity(entityDescriptor.getName(), entityDescriptor.getId());
        entity.setPosition(entityDescriptor.getPosition());
        return entity;
    }

    protected GameEngine getGameEngine() {
        return mGameEngine;
    }

    protected EntityRegistry getEntityRegistry() {
        return mEntityRegistry;
    }

}
