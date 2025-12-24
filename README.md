# qbit-user-role-permissions

Role-based access control for QQQ applications.

**For:** QQQ developers who need to restrict what users can see and do based on their assigned roles  
**Status:** Stable

## Why This Exists

Most business applications need access control. Without it, every user sees everything and can do anything. Building RBAC from scratch means creating tables, writing permission checks, building admin UIs, and wiring it all together.

This QBit provides a complete RBAC system that integrates directly with QQQ's security layer. Define roles, assign permissions, and QQQ enforces them automatically across entities, processes, and dashboard screens.

## Features

- **Role Management** - Create roles with hierarchical inheritance
- **Entity Permissions** - Control read, insert, update, delete per entity per role
- **Field-Level Security** - Hide or make specific fields read-only by role
- **Process Permissions** - Restrict which roles can execute which processes
- **Record-Level Filtering** - Apply automatic filters so users only see their own data
- **Dashboard Integration** - Navigation and screens respect permissions automatically

## Quick Start

### Prerequisites

- QQQ application (v0.20+)
- Database backend configured

### Installation

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>io.qrun</groupId>
    <artifactId>qbit-user-role-permissions</artifactId>
    <version>0.3.0</version>
</dependency>
```

### Register the QBit

```java
public class AppMetaProvider extends QMetaProvider {
    @Override
    public void configure(QInstance qInstance) {
        // Register the QBit
        new UserRolePermissionsQBit().configure(qInstance);
        
        // Your entities, processes, etc.
    }
}
```

### Define Roles

```java
new QRoleMetaData()
    .withName("admin")
    .withLabel("Administrator")
    .withPermissions(QPermission.allTablesFullAccess());

new QRoleMetaData()
    .withName("viewer")
    .withLabel("Read Only")
    .withPermissions(QPermission.allTablesReadOnly());
```

## Usage

### Entity Permissions

```java
// Full access to orders, read-only for customers
new QRoleMetaData()
    .withName("sales")
    .withPermission("order", QPermission.fullAccess())
    .withPermission("customer", QPermission.readOnly());
```

### Field-Level Restrictions

```java
// Hide salary field from non-HR roles
new QFieldPermission()
    .withTable("employee")
    .withField("salary")
    .withRoles("hr", "admin")  // Only these roles can see it
    .withOthersCanRead(false);
```

### Record-Level Filtering

```java
// Users only see their own records
new QRecordSecurityLock()
    .withTable("order")
    .withFieldName("createdByUserId")
    .withLockType(RecordSecurityLock.LockType.READ_AND_WRITE);
```

### Process Permissions

```java
// Only admins can run data import
new QProcessMetaData()
    .withName("dataImport")
    .withPermissionRoles("admin");
```

## Configuration

The QBit creates these tables automatically:

| Table | Purpose |
|-------|---------|
| `role` | Role definitions |
| `user_role` | User-to-role assignments |
| `permission` | Role-to-entity permissions |

Override table names in configuration if needed:

```java
new UserRolePermissionsQBit()
    .withRoleTableName("app_role")
    .withUserRoleTableName("app_user_role");
```

## Project Status

Stable and production-ready. Used across multiple QQQ deployments.

### Roadmap

- Permission caching improvements
- Audit logging for permission changes
- UI for permission matrix editing

## Contributing

1. Fork the repository
2. Create a feature branch
3. Run tests: `mvn clean verify`
4. Submit a pull request

## License

Proprietary - QRun.IO
