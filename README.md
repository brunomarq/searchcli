# Getting Started

This is an example of a command line application written in Java using Spring Framework.

The purpose of the CLI is to:
- load the data available in 3 json files related to organizations, users and tickets;
- allow searching through that data in an efficient manner based on inputs such as the entity type (organization, user or ticket) and a field of that entity, and the value to be used as the filter for fetching results;
- display the results of a search and data related to its associations with the other entities in human readable format.


## Pre-requisites

The pre-requisites for running this application are:
- git client to be able to clone this repo;
- docker to be able to build and run the searchcli image.


## How to build it

In order to build and run the CLI, perfom the following steps:

1. Clone this repo
```
git clone git@github.com:brunomarq/searchcli.git
```

2. Build the docker image
```
cd searchcli
docker build -t searchcli .
```

3.1. Run it using the default organizations.json, tickets.json and users.json
```
docker run -it searchcli
```

3.2. Run it providing your own organizations.json, tickets.json and users.json
```
docker run -it docker run -it -v <directory in the host machine containing the 3 json files>:/mydata searchcli
```

After launching it, you should see a `shell:>` prompt waiting for your command.

## How to use it

1. Load the organization, ticket and user json files and prepare the inverted indexes.
```
shell:> load-databases
```
Notice, that the other commands (`search` and `field`) will not be available until you run the `load-database` command successfuly.

If you want to use your own json files, pass the mapped volumen to the `load-database` command. For example:
```
shell:> load-database /mydata
```

2. List the available fiels in the entity type you want to perform the search on. For example,
```
shell:> fields organizations
```

3. Perform the search. For example:
```
shell:> search organization _id 101
shell:> search organization shared_tickets false
shell:> search organization tags West
```


By typing help, you should be able to get more details about all the avaible commands.

```
shell:>help

AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Search CLI
        fields: Show available search field for tickets, users or organisations.
        load-database: Load organization, ticket and user json files and prepare the inverted indexes.
        search: Search for tickets, users or organisations.
```

```
NAME
        load-database - Load organization, ticket and user json files and prepare the inverted indexes.

SYNOPSYS
        load-database [[--directory-path] string]  

OPTIONS
        --directory-path  string

                [Optional, default = ]
```


```
NAME
        fields - Show available search field for tickets, users or organisations.

SYNOPSYS
        fields [--entity] string  

OPTIONS
        --entity  string

                [Mandatory]
```

```
NAME
        search - Search for tickets, users or organisations.

SYNOPSYS
        search [--entity] string  [--field] string  [[--value] string]  

OPTIONS
        --entity  string

                [Mandatory]

        --field  string

                [Mandatory]

        --value  string

                [Optional, default = ]
```
    


## Assumptions

- the `_id` field is used as a unique identifier
- the JSON files are always named: organizations.json, tickets.json and users.json
- when searching through json array type of fields, the value of the search should contain one array item only.
- fields representing dates are handled as strings
- fields representing boolean are also handled as strings
- when searching through free text fields, the search only supports full match

### Architecture

The design of the application was based on the software design pattern MVC with the natural choice of the Repository-Service pattern.

The structure directory reflects that decision with the main folder under the `src` directory being:
- utils: package containing utility classed for constants, input validators and response formatters used to support the view.
- controller: package containing the class the implements the shell commands (based on Spring Shell). This package consumes data transfer objects created by the service layer with all the necessary relationship to display all the relevant information of a search.
- service: package containing the business logic able to fetch models from the repository, and assemble them in data transfer object to be consumed by the controller and view.
- repository: package containing a repository for each entity type. Each repository contains its own inverted index that allows for an efficient search.
- dto: package containing POJOs that are used to represent all the relevant information to be displayed on a search result.
- domain: package containing the main models of the application and closely related to the representation of the json files.
- exception: package containing custom exception used by the application.

### Technologies

I'm using MVC pattern, an inverted index to implement the full text search, Java 11 with Spring Boot, Spring Shell, Jackson for parsing JSON content and TDD using SpringBootTest(JUnit5, Mockito, AssertJ). 

I'm also taking this opportunity to use VSCode as the IDE instead of Eclipse or IntelliJ and, I'm actually impressed on how good the VSCode support is for Java currently as long as you install the Java Extension Pack, SpringBoot Extension Pack, JavaDoc Tools and Sonarlint plugins.

