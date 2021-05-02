package cn.leaf.freemq.core.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultEventManagerTest {
    private EventListener listener1;
    private EventListener listener2;
    private EventManager eventManager;


    @BeforeEach
    public void beforeEach() {
        listener1 = Mockito.mock(EventListener.class);
        listener2 = Mockito.mock(EventListener.class);
        eventManager = new DefaultEventManager();
    }

    @Test
    public void registerEventListener() {
        eventManager.registerEventListener(listener1);
        eventManager.registerEventListener(listener2);
    }

    @Test
    public void publishEvent() {
        FmqEvent event = Mockito.mock(FmqEvent.class);
        eventManager.registerEventListener(listener1);
        eventManager.registerEventListener(listener2);

        eventManager.publish(event);
        eventManager.publish(event);
        eventManager.publish(event);

        Mockito.verify(listener1, Mockito.times(3)).accept(event);
        Mockito.verify(listener2, Mockito.times(3)).accept(event);


    }

}