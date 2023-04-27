# HCF CORE

A simple core focused on the HCF modality, in order to provide everything needed to run this kind of modality

# Important
This is a project with more than 3 years old, I may be quite outdated in several dependencies and structure.

## API

### Static methods
````
API#findUser(id);
API#findKit(id);
API#findCrate(id);
API#findFation(id);
````

### Custom Events

#### Faction Events
```
FactionCreateEvent
FactionDisbandEvent
FactionRenameEvent
UserJoinFactionEvent
UserLeftFactionEvent
```

#### User events
```
UserJoinEvent
UserLeftEvent
```
