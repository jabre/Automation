package common;

import java.util.Map;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.testng.ITestResult;
import org.testng.Reporter;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Log {
	protected Logger logger;
	private Multimap<String, String> messages;

	public Log() {
		this(new Exception().getStackTrace()[1].getClassName());
	}

	public Log(String className) {
		if (className == null)
			logger = Logger.getLogger(UUID.randomUUID().toString());
		else
			logger = Logger.getLogger(className);
		
		Handler handler = new ConsoleHandler();
		handler.setFormatter(formatter());
		logger.addHandler(handler);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);

		messages = LinkedListMultimap.create();
	}

	private Formatter formatter() {
		return new Formatter() {
			public String format(LogRecord record) {
				String level = record.getLevel().getName();
				switch (level) {
				case "SEVERE":
					level = "ERROR";
					break;
				default:
					level = record.getLevel().getName();
				}
				
				if (record.getMessage().equals(""))
					return "\n";
				return level + " : " + record.getMessage() + "\n";
			}
		};
	}

	public void info(String msg) {
		messages.put("info", msg);
	}

	public void warning(String msg) {
		messages.put("warning", msg);
	}

	public void error(String msg) {
		messages.put("severe", msg);
	}

	protected synchronized void outputLog(ITestResult result) {
		for (Map.Entry<String, String> entry : messages.entries()) {
			String message = entry.getValue();

			Reporter.setCurrentTestResult(result);
			switch (entry.getKey()) {
			case "info":
				logger.info(message);
				Reporter.log("INFO : " + message);
				break;
			case "warning":
				logger.warning(message);
				Reporter.log("WARNING : " + message);
				break;
			case "severe":
				logger.severe(message);
				Reporter.log("ERROR : " + message);
				break;
			default:
				logger.info(message);
				Reporter.log("INFO : " + message);
			}
		}
		logger.info("");
		Reporter.log("");
		messages.clear();
	}
}
