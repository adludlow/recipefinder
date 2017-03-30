import Html exposing (..)
import Html.Attributes exposing (..)
import Html.Events exposing (onInput, onClick)
import String exposing (..)
import Json.Decode exposing (..)
import Http 
import Json.Decode as Decode

main =
  Html.program{ init = init, view = view, update = update, subscriptions = subscriptions }


-- MODEL

type alias Model = {
    searchString : String,
    displayString : String,
    url : String,
    searchError : String
}

init : (Model, Cmd Msg)
init  =
    (Model "" "" "" "", Cmd.none)

-- UPDATE

type Msg
    = SearchString String |
      Search |
      SearchResult (Result Http.Error String)

update : Msg -> Model -> (Model, Cmd Msg)
update msg model =
  case msg of
    SearchString searchString ->
        ({ model | searchString = searchString}, Cmd.none )

    Search ->
        ({ model | displayString = model.searchString}, executeSearch model.searchString)

    SearchResult (Ok recipeUrl) ->
        ({ model | url = recipeUrl}, Cmd.none)

    SearchResult (Err error) ->
        ({model | searchError = toString error}, Cmd.none)


executeSearch : String -> Cmd Msg
executeSearch searchString =
    let 
        url = "http://localhost:8080/recipes/1"

        request = Http.get url decodeRecipeUrl
    in
       Http.send SearchResult request

decodeRecipeUrl : Decode.Decoder String
decodeRecipeUrl =
    Decode.at ["url"] Decode.string

-- VIEW
view : Model -> Html Msg
view model =
  div []
    [ input [ type_ "text", placeholder "Search String", onInput SearchString] [],
      button [onClick Search] [ text "Search" ],
      viewResult model
    ]

viewResult : Model -> Html msg
viewResult model =
    let
        message =
        if length model.displayString > 0 then
           "You searched for " ++ model.displayString
        else
           ""
    in
        div[] [div [style [("color", "blue")] ] [text message],
        div [][text model.url],
        div [style [("color", "red")]] [text model.searchError]]

subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none
