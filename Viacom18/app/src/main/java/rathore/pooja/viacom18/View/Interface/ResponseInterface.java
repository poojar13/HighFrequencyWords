package rathore.pooja.viacom18.View.Interface;

import org.jsoup.nodes.Document;

public interface ResponseInterface {
    void onSuccess(Document document);

    void onFailure(String message);
}
