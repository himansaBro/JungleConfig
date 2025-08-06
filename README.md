# JungleConfig

**JungleConfig** is a **modular, swappable, lightweight, dependency-free, platform-agnostic, and format-agnostic** configuration framework for Java.

It’s not just another config library.  
It’s a **configuration engine** — designed to work *anywhere*, store *anything*, and adapt *instantly*.

Whether you're building a desktop app, an embedded system, or a secure microservice — JungleConfig gives you **full control** over how, where, and what you store.

---

## 🌟 Main Features

### 1. Unified Configuration API
- **Fluent and intuitive** interface:
  ```java
  config.Set("user.name", "Himansa").Set("user.age", 19);
  String name = config.get("user.name", String.class);
  Optional<Integer> age = config.Get("user.age", Integer.class);
  ```
- Supports **every data type**:
  - **Primitives**: `int`, `boolean`, `char`, `LocalDate`, etc.
  - **POJOs** (via Jackson or custom adapters)
  - **Collections**: `List`, `Map`, etc.
  - **Custom types** (via `TypeConverterAdapter`)
- **Optional<T> support** for safe, null-free access

---

### 2. Pluggable Persistence Architecture

JungleConfig is built on **interfaces, not assumptions**.  
Swap any layer — change where or how data is stored — **without touching your business logic**.

#### 🔹 `IOHandlerInterface` — Where is data saved?
- Manages low-level **read/write operations**
- Fully swappable — store config in:
  - Files ✅
  - REST endpoints 🌐
  - EEPROM / SPI Flash 💾 (embedded systems)
  - Images, videos, QR codes 🖼️ (steganography)
  - In-memory (for testing)

**Built-in IO Handlers:**
- `NativeIOHandler` — Saves to file
- `InMemoryIOHandler` — In-memory storage (perfect for unit tests)

---

#### 🔹 `ConverterInterface` — How is data encoded?
- Converts internal `TypeMap` to raw format and back
- Swap to support different **languages and formats**:
  - `Key:type=value` (default)
  - JSON, XML, YAML, INI, CSV, HTML
  - Binary, encrypted blobs, custom formats

**Built-in Converters:**
- `NativeConverter` — Uses `key:type=value` format
  - POJOs → JSON (URL-encoded)
  - Type-safe with tags: `user.age:Integer=19`
- `NativeFlatJsonConverter` — Stores entire config as flat JSON

---

#### 🔹 `CacheInterface` — Caching & Transaction Layer

Split into two clean interfaces for maximum flexibility:

##### 📦 `InternalCacheInterface`
- Pure caching contract
- Plug in any caching library: Caffeine, Redis, Ehcache, or custom
- Enables fast, consistent reads

**Built-in Caches:**
- `NativeInternalCache` — Lightweight, counter-based cache
  - Auto-flush after N writes
  - Auto-reload after N reads to avoid staleness

##### ⚙️ `InternalServiceInterface`
- Handles **transactions**, **queries**, and **metadata**
- Swap transaction models or query engines easily

**Built-in Services:**
- `NativeInternalTransaction` — Full transaction support:
  - `BeginTransaction()`
  - `Commit()`, `Rollback()`, `EndTransaction()`
  - Safe, staged updates with rollback-to-savepoint

##### 🧩 Combined: `NativeExtendedCache`
- **Recommended**: Combines `InternalCache` + `InternalService`
- Modern, modular, and extensible
- Replaces the old monolithic `NativeCache`

> 💡 **Legacy Note**: `NativeCache` is the original all-in-one implementation — still works, but deprecated in favor of the split model.

---

### 3. Type System & Adapters

#### 🔁 `TypeConverter` + `TypeConverterAdapter`
- Efficient, type-safe conversion without forcing Jackson
- Use **custom adapters** for domain types:
  - `Color`, `Path`, `Duration`, `Enum`, etc.
- Jackson is **optional** — used only when needed (e.g., POJOs)

**Built-in Adapters:**
- `NativeBooleanAdapter`
- `NativeStringAdapter`
- `NativeIntegerAdapter`
- (Extensible — add your own!)

---

## 🆚 Why JungleConfig Is Different

| Feature | Most Config Libraries | JungleConfig |
|--------|------------------------|------------|
| **Extensibility** | Fixed format & storage | Swap **anything** via interfaces |
| **Transactions** | ❌ None | ✅ Full support with rollback |
| **Type Safety** | Strings only | ✅ `key:type=value` tagging |
| **Caching** | ❌ None or basic | ✅ Smart, auto-syncing |
| **Storage Flexibility** | File-only | ✅ File, REST, image, EEPROM, etc. |
| **Format Support** | JSON/Properties | ✅ Any format via `Converter` |
| **Zero Dependencies** | Often need frameworks | ✅ Pure Java (Jackson optional) |

---

## 🚀 Use Cases

- **Desktop Apps** — Save user settings safely
- **Embedded Systems** — Run on microcontrollers with EEPROM
- **Secure Services** — Use encrypted config
- **Dynamic Tools** — Query and introspect config at runtime
- **Testing** — In-memory mode for fast, isolated tests
- **Creative Storage** — Hide config in images, videos, or audio

---

## 💡 Design Philosophy

> **"The data is yours. The format is yours. The storage is yours.  
> JungleConfig just helps you manage it — without getting in the way."**

It’s not about enforcing rules.  
It’s about **giving you freedom** — with structure.

---

## 🛠️ Getting Started (Example)

```java
File configFile = new File("app.conf");
JungleConfig config = new JungleConfig(configFile);

config.Set("user.name", "Himansa");
config.Set("app.debug", true);
config.SetPOJO("user.profile", new User("Himansa", 19));

String name = config.get("user.name", String.class);
User profile = config.get("user.profile", User.class);
```

Want encryption? Just swap the converter:
```java
JungleConfig encrypted = JungleConfig.EncryptedConfig(file, "s3cr3t");
```

Want REST? Just plug in your `RestIOHandler`.

---

## 🌿 JungleConfig: Config With Claws.

Flexible. Fast. Fierce.

> *"It's not a config library. It's a persistence engine."*
