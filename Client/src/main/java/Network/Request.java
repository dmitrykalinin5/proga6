package Network;

import Collections.Ticket;

import java.io.Serializable;

public record Request(String commandName, Ticket argument) implements Serializable {}