pub fn get_all_cards() {
    let mut cards: Vec<String> = Vec::new();
    for i in 9 {
        cards.push("wan_" + i);
        cards.push("tiao_" + i);
        cards.push("tong_" + i);
    }
    for i in 8 {
        cards.push("hua_" + i);
    }
    for i in 4 {
        cards.push("kong_" + i);
    }
    return cards
}
