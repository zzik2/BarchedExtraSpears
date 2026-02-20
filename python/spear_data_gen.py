"""
Spear Data Generator
====================
Generates SpearData JSON files based on vanilla reference values.

Usage examples:
  # 1.2x stronger than IRON
  python spear_data_gen.py bronze stronger IRON 1.2

  # 1.2x stronger than IRON, but capped at DIAMOND
  python spear_data_gen.py bronze stronger IRON 1.2 --cap DIAMOND

  # Between STONE and GOLD (50/50 mix)
  python spear_data_gen.py bronze between STONE GOLD

  # Between STONE and GOLD, 70% toward GOLD
  python spear_data_gen.py bronze between STONE GOLD --ratio 0.7

  # 1.5x weaker than NETHERITE
  python spear_data_gen.py bronze weaker NETHERITE 1.5

  # 1.5x weaker than NETHERITE, but at least as strong as DIAMOND
  python spear_data_gen.py bronze weaker NETHERITE 1.5 --floor DIAMOND
"""

import argparse
import json
import os
import sys

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
REF_PATH = os.path.join(SCRIPT_DIR, "ref", "vanilla.json")
OUTPUT_DIR = os.path.join(SCRIPT_DIR, "output")

BUFF_UP = True
BUFF_DOWN = False

ATTR_BUFF_DIRECTION = {
    "swingSeconds":                BUFF_DOWN,
    "kineticDamageMultiplier":     BUFF_UP,
    "delaySeconds":                BUFF_DOWN,
    "damageCondDurationSeconds":   BUFF_UP,
    "damageCondMinSpeed":          BUFF_DOWN,
    "knockbackCondDurationSeconds": BUFF_UP,
    "knockbackCondMinSpeed":       BUFF_DOWN,
    "dismountCondDurationSeconds": BUFF_UP,
    "dismountCondMinRelativeSpeed": BUFF_DOWN,
}

ATTR_ORDER = list(ATTR_BUFF_DIRECTION.keys())


def load_reference():
    with open(REF_PATH, "r", encoding="utf-8") as f:
        return json.load(f)


def get_tier(ref: dict, name: str) -> dict:
    key = name.upper()
    if key not in ref:
        available = ", ".join(ref.keys())
        print(f"Error: Unknown tier '{name}'. Available: {available}")
        sys.exit(1)
    return ref[key]


def apply_stronger(base: dict, multiplier: float) -> dict:
    result = {}
    for attr in ATTR_ORDER:
        val = base[attr]
        if ATTR_BUFF_DIRECTION[attr] == BUFF_UP:
            result[attr] = val * multiplier
        else:
            result[attr] = val / multiplier
    return result


def apply_weaker(base: dict, multiplier: float) -> dict:
    result = {}
    for attr in ATTR_ORDER:
        val = base[attr]
        if ATTR_BUFF_DIRECTION[attr] == BUFF_UP:
            result[attr] = val / multiplier
        else:
            result[attr] = val * multiplier
    return result


def apply_between(tier_a: dict, tier_b: dict, ratio: float) -> dict:
    result = {}
    for attr in ATTR_ORDER:
        a = tier_a[attr]
        b = tier_b[attr]
        result[attr] = a + (b - a) * ratio
    return result


def clamp_stronger(values: dict, cap_tier: dict) -> dict:
    result = {}
    for attr in ATTR_ORDER:
        val = values[attr]
        cap = cap_tier[attr]
        if ATTR_BUFF_DIRECTION[attr] == BUFF_UP:
            result[attr] = min(val, cap)
        else:
            result[attr] = max(val, cap)
    return result


def clamp_weaker(values: dict, floor_tier: dict) -> dict:
    result = {}
    for attr in ATTR_ORDER:
        val = values[attr]
        floor = floor_tier[attr]
        if ATTR_BUFF_DIRECTION[attr] == BUFF_UP:
            result[attr] = max(val, floor)
        else:
            result[attr] = min(val, floor)
    return result


def round_values(values: dict, precision: int = 4) -> dict:
    return {k: round(v, precision) for k, v in values.items()}


def save_output(name: str, values: dict):
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    path = os.path.join(OUTPUT_DIR, f"{name}.json")
    ordered = {k: values[k] for k in ATTR_ORDER}
    with open(path, "w", encoding="utf-8") as f:
        json.dump(ordered, f, indent=2)
    print(f"Saved: {path}")


def print_comparison(name: str, values: dict, ref: dict, base_name: str):
    base = ref.get(base_name.upper(), {})
    print(f"\n{'='*60}")
    print(f"  Generated: {name}")
    print(f"{'='*60}")
    print(f"  {'Attribute':<32} {'Value':>10}  {'Base':>10}  {'Diff':>8}")
    print(f"  {'-'*32} {'-'*10}  {'-'*10}  {'-'*8}")
    for attr in ATTR_ORDER:
        val = values[attr]
        direction = "↑" if ATTR_BUFF_DIRECTION[attr] == BUFF_UP else "↓"
        if base:
            b = base[attr]
            diff = val - b
            sign = "+" if diff > 0 else ""
            print(f"  {attr:<30}{direction} {val:>10.4f}  {b:>10.4f}  {sign}{diff:>7.4f}")
        else:
            print(f"  {attr:<30}{direction} {val:>10.4f}")
    print(f"{'='*60}\n")


def cmd_stronger(args):
    ref = load_reference()
    base = get_tier(ref, args.base)
    result = apply_stronger(base, args.multiplier)

    if args.cap:
        cap_tier = get_tier(ref, args.cap)
        result = clamp_stronger(result, cap_tier)

    result = round_values(result)
    print_comparison(args.name, result, ref, args.base)
    save_output(args.name, result)


def cmd_weaker(args):
    ref = load_reference()
    base = get_tier(ref, args.base)
    result = apply_weaker(base, args.multiplier)

    if args.floor:
        floor_tier = get_tier(ref, args.floor)
        result = clamp_weaker(result, floor_tier)

    result = round_values(result)
    print_comparison(args.name, result, ref, args.base)
    save_output(args.name, result)


def cmd_between(args):
    ref = load_reference()
    tier_a = get_tier(ref, args.tier_a)
    tier_b = get_tier(ref, args.tier_b)
    result = apply_between(tier_a, tier_b, args.ratio)
    result = round_values(result)
    print_comparison(args.name, result, ref, args.tier_a)
    save_output(args.name, result)


def main():
    parser = argparse.ArgumentParser(
        description="Spear Data Generator — Generate balanced SpearData JSON files",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__
    )
    parser.add_argument("name", help="Output material name (e.g. bronze)")

    sub = parser.add_subparsers(dest="command", required=True)

    # stronger
    p_stronger = sub.add_parser("stronger", help="Generate data stronger than a base tier")
    p_stronger.add_argument("base", help="Base tier name (e.g. IRON)")
    p_stronger.add_argument("multiplier", type=float, help="Strength multiplier (e.g. 1.2)")
    p_stronger.add_argument("--cap", help="Cap tier — result won't exceed this tier's strength")

    # weaker
    p_weaker = sub.add_parser("weaker", help="Generate data weaker than a base tier")
    p_weaker.add_argument("base", help="Base tier name (e.g. NETHERITE)")
    p_weaker.add_argument("multiplier", type=float, help="Weakness multiplier (e.g. 1.5)")
    p_weaker.add_argument("--floor", help="Floor tier — result won't be weaker than this tier")

    # between
    p_between = sub.add_parser("between", help="Generate data between two tiers")
    p_between.add_argument("tier_a", help="First tier (weaker end)")
    p_between.add_argument("tier_b", help="Second tier (stronger end)")
    p_between.add_argument("--ratio", type=float, default=0.5,
                           help="Interpolation ratio: 0.0 = tier_a, 1.0 = tier_b (default: 0.5)")

    args = parser.parse_args()

    if args.command == "stronger":
        cmd_stronger(args)
    elif args.command == "weaker":
        cmd_weaker(args)
    elif args.command == "between":
        cmd_between(args)


if __name__ == "__main__":
    main()
