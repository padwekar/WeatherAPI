package bytemarktask.lifesavi.app.weatherx.ui.weather.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bytemarktask.lifesavi.app.weatherx.R
import bytemarktask.lifesavi.app.weatherx.app.constant.Constant
import bytemarktask.lifesavi.app.weatherx.data.model.Forecast
import bytemarktask.lifesavi.app.weatherx.utils.DateUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class DetailWeatherAdapter(var forecastList: MutableList<Forecast> = mutableListOf()) : RecyclerView.Adapter<ViewHolder>() {

    var presenter: DetailWeatherPresenter? = null
    var lastPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false))
    }

    override fun getItemCount(): Int {
        return forecastList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val forecast = forecastList[position]

        holder.itemView.run {
            Picasso.get().load(String.format(Constant.NetworkUrl.BaseImageUrl, forecast.weather.icon)).into(imageViewCondition)
            textViewDay.text = DateUtils.getFormattedDate(forecast.valid_date, "dd MMM")
            textViewTemperature.text = String.format(forecast.temp.toString() + "°C")
            when (forecast.isSelected) {
                true -> indicatorView.visibility = View.VISIBLE
                else -> indicatorView.visibility = View.INVISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            if (lastPosition == position) {
                return@setOnClickListener
            }

            if (lastPosition > -1) {
                forecastList[lastPosition].isSelected = false
                notifyItemChanged(lastPosition)
            }

            forecastList[position].isSelected = true
            lastPosition = position
            notifyItemChanged(lastPosition)

            presenter?.onWeatherDateSelected(forecast)
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view)