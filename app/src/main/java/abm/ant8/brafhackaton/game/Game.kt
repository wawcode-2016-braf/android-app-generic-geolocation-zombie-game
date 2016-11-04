package abm.ant8.brafhackaton.game

import com.fasterxml.jackson.annotation.JsonProperty

data class Game(@JsonProperty("start_date") val startDate: String,
                val players: List<Player>,
                @JsonProperty("next_visible") val nextVisible: String,
                @JsonProperty("last_visible") val lastVisible: String)