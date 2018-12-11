package network;

import java.util.EventListener;

import Messages.Message;

public interface NewMessageListener extends EventListener {
	void aMessageHasBeenReceived(Message msg_event);
}
