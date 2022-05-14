import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class CSubscriber implements Subscriber<CMessage> {

    private Subscription subscription;

    CSubscriber() {

    }

    @Override
    public void onSubscribe(final Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(CMessage item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

}
