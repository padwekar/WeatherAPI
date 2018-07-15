package bytemarktask.lifesavi.app.weatherx.data.subject

import bytemarktask.lifesavi.app.weatherx.data.model.ForecastResponse
import io.reactivex.subjects.PublishSubject

class Event {
    companion object {
        var searchEvent = PublishSubject.create<ForecastResponse>()
    }
}