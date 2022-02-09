use crate::get_all_cards;

#[cfg(test)]
mod tests {
    #[test]
    fn card() {
        let cards = get_all_cards();
        println!("{}", cards)
    }
}
