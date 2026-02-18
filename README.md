# JungleConfig 🌴

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-19%2B-blue.svg)](https://www.oracle.com/java/technologies/javase-jdk19-downloads.html)

**JungleConfig** is a high-performance, developer-friendly configuration management library for Java. It goes beyond simple key-value storage by providing a **fully swappable, modular architecture** that allows you to customize every layer—from storage backends to data formats—without changing your application code.

Built with reliability in mind, JungleConfig features multi-layer caching, ACID-like transactions, and robust security.

---

## 🏗️ Architecture: The Swappable Philosophy

The core strength of JungleConfig is its "LEGO-like" modularity. Every component is an interface that can be swapped or extended:

- **IO Handlers**: Change *where* data is stored. Swap a local `File` handler for a `RESTIOHandler` (to save to an endpoint), a `DatabaseIOHandler`, or `S3IOHandler`.
- **Converters**: Change *how* data is formatted. Swap the default JSON structure for `XML`, `YAML`, or even a custom binary format.
- **Type Adapters**: Extend *what* can be stored. Add support for any custom class by implementing a simple `TypeConverterAdapter`.
- **Service Layers**: Decorate the core with optional features like `NativeEncryptedConverter`, `NativeExtendedCache`, or `NativeInternalTransaction`.

**The API remains constant.** Your code calls `config.get()` and `config.set()`, regardless of whether the data is in an encrypted file on disk or a flat JSON map in a remote cloud service.

---

## ⚡ Key Features

- � **Fully Modular**: Swap IO, Formats, Caching, and Security layers effortlessly.
- �🔐 **Solid Security**: AES-256 GCM encryption with PBKDF2 key derivation.
- 🔄 **ACID Transactions**: Atomic operations with `commit()` and `rollback()` support.
- 🚀 **Multi-Layer Caching**: High-speed internal and extended caching for near-zero latency.
- 🛠️ **Rich Type Support**: Built-in adapters for Primitives, UUID, Java 8 Time API, and POJOs.
-  **Regex Querying**: Powerful key/value/type searching using regular expressions.
- 💾 **Safe Persistence**: Automatic backups and atomic write operations.

---

## � Quick Start: The "Custom Stack"

While JungleConfig provides convenient factory methods like `InMemoryConfig()`, its true power lies in manual assembly for custom needs:

```java
// Example: Creating a custom stack with a remote REST backend and XML formatting
JungleConfig config = new JungleConfig(
    new NativeTypeConverter(
        new NativeExtendedCache(
            new NativeInternalTransaction(
                new NativeInternalCache(
                    new CustomXmlConverter(          // Swapped Converter
                        new RestIOHandler(endpoint)   // Swapped IO Handler
                    ), 10, 100, true
                )
            )
        ), true, getMyCustomAdapters()               // Custom Type Support
    )
);
```

---

## 🛡️ Usage Highlights

### 1. Simple Primitives & POJOs
```java
config.Set("server.port", 8080);
config.SetPOJO("auth.admin", new User("Himansa", 19));

int port = config.get("server.port", Integer.class);
User admin = config.get("auth.admin", User.class);
```

### 2. Transaction Management
Ensures atomicity across multiple configuration changes.
```java
config.BeginTransaction();
try {
    config.Set("system.status", "MAINTENANCE");
    config.Commit();
} catch (Exception e) {
    config.Rollback();
}
```

### 3. Integrated Security
```java
JungleConfig secureConfig = JungleConfig.EncryptedConfig(file, "password");
secureConfig.Set("db.password", "secret");
```

---

## 📖 API Summary

- `Set(key, value)` / `SetPOJO(key, object)`: Store data.
- `Get(key, class)`: Retrieve as `Optional<T>`.
- `getCollection(key, typeRef)`: Retrieve complex collections (e.g., `List<User>`).
- `query(keyReg, typeReg, valReg)`: Find keys using Regex.
- `Backup(file, override)`: Export current state.
- `BeginTransaction()` / `Commit()` / `Rollback()`: Manage atomic updates.

---

## ⚖️ License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
<p align="center">Made with ❤️ by CodeHack</p>
