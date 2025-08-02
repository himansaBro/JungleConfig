# JungleConfig (JConfig)
# JConfig

A lightweight, type-safe, file-based configuration library for Java.  
Save and load typed key-value data, including POJOs, with support for custom adapters, transactions, and flat key tagging — all in a simple URL-safe encoded format.

---

## Features

- Flat-file key-value config with typed values  
- URL-safe encoding for safe file storage  
- Custom adapters for handling any data types  
- Transaction support (`BeginTransaction`, `Commit`, `Rollback`)  
- Optional in-memory caching for performance  
- POJO serialization/deserialization using Jackson  
- Tag-style key prefixes for filtering and grouping  
- Validation to avoid invalid keys (no `:`, `=`, `\n`)  
- Minimal dependencies (only Jackson for JSON)  
- Designed for easy embedding in small to medium Java projects  
- Planned support for encryption and programmable backups  

---

## Getting Started

### Adding JConfig to Your Project

Since this is a custom library, simply include the compiled JAR in your project's classpath or import the source directly.

You will need [Jackson Databind](https://github.com/FasterXML/jackson-databind) and related Jackson modules for JSON serialization.

---

### Basic Usage

```java
// Initialize with config file and caching enabled
ConfigHandler config = new ConfigHandler(new File("config.txt"), true);

// Set typed values
config.set("user.name", "Himansa");
config.set("user.age", 19);
config.set("user.isActive", true);

// Get values safely using Optional
Optional<String> name = config.get("user.name", String.class);
name.ifPresent(System.out::println);

// Transaction example
config.BeginTransaction();
config.set("version.id", "0.0.1");
config.set("version.name", "BetaBuild");
config.Commit();

// Save/load POJOs
record Person(int id, String name){}
Person sam = new Person(12, "Sam");
config.SerializedSet("person.sam", sam);

Optional<Person> loadedSam = config.SerializedGet("person.sam", Person.class);
loadedSam.ifPresent(p -> System.out.println(p.name));
````

---
## Static Mode (`JConfig`)

For quick and easy usage without manually creating `ConfigHandler` instances, `JConfig` provides a static singleton wrapper around the default config file (`JConfig.config`).

You can call these static methods anywhere in your project to interact with the config:

```java
import core.JConfig;
import core.dataTypes.JConfigConverter;

// Set values directly
JConfig.Set("user.name", "Himansa");
JConfig.Set("user.age", 19);

// Get values safely with Optional
Optional<String> nameOpt = JConfig.SafeGet("user.name", String.class);
nameOpt.ifPresent(System.out::println);

// Get value directly (throws if missing)
String name = JConfig.Get("user.name", String.class);

// Transaction example
JConfig.BeginTransaction();
JConfig.Set("version.id", "0.0.1");
JConfig.Set("version.name", "BetaBuild");
JConfig.Commit();

// Register custom adapters globally
JConfig.AddAdapters(new MyCustomAdapter());

// Query keys by prefix
var versionKeys = JConfig.KeysStartsWith("version.");
for (var entry : versionKeys.getEntryList()) {
    System.out.println(entry.getKey() + ": " + entry.getValue2());
}

// Serialize/Deserialize POJOs
record Person(int id, String name) {}
Person sam = new Person(12, "Sam");
JConfig.SerializedSet("person.sam", sam);
Optional<Person> loadedSam = JConfig.SerializedGet("person.sam", Person.class);
loadedSam.ifPresent(p -> System.out.println(p.name));
```

## Advanced Features

* **Custom Adapters:** Extend `JConfigConverter<T>` to add your own serializers.
* **Key Filtering:** Use `findSimilar("prefix")` to get all keys starting with a prefix.
* **Cache Management:** Automatically updates cache with changes, or can be invalidated manually.
* **Safe Key Validation:** Keys cannot contain `:`, `=`, or newline characters to prevent file corruption.

---

## Design Philosophy

JConfig is built for developers who want a simple but powerful file-based config with **type safety**, **customizability**, and **transactional integrity** — without heavy dependencies or complicated setups.

---

## License

This project is licensed under the MIT License. See `LICENSE` for details.

---

## Author

Developed by Himansa.
Inspired by the desire to build simple, clean, and effective tools for everyday programming challenges.

---

## Contributions

Contributions, suggestions, and issues are welcome! Feel free to open an issue or pull request.

---

## Contact

For questions or support, reach out at \[[himansarajapacksha@gmail.com](mailto:himansarajapacksha@gmail.com)].

---
