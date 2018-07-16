/**
 * Created by forev on 7/15/2018.
 */
renderMathInElement(
    document.body,
    {
        delimiters: [
            {left: "$$", right: "$$", display: true},
            {left: "\\[", right: "\\]", display: true},
            {left: "$", right: "$", display: false},
            {left: "\\(", right: "\\)", display: false}
        ]
    }
);
